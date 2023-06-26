package com.example.novustoolkit.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.novustoolkit.roomdb.*
import com.example.novustoolkit.roomdb.expensemanager.*
import com.example.novustoolkit.roomdb.expensemanager.Category.category
import com.example.novustoolkit.roomdb.notemanager.*
import com.example.novustoolkit.roomdb.passwordmanager.*
import com.example.novustoolkit.roomdb.taskmanager.*
import com.example.novustoolkit.ui.theme.screens.feature_screen.password_manager.generatePass
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

@OptIn(ExperimentalCoroutinesApi::class)
class SharedViewModel(
    private val taskdao: tasksDao,
    private val passwordDao: passwordDao,
    private val notesDao: notesDao,
    private val expensedao :ExpenseDao,
    private val budgetDao :budgetDao
): ViewModel() {
    //task manager logic starts
     private val task_state = MutableStateFlow(TaskState())
     private val _sortType = MutableStateFlow(Priority.High)

     private val _tasks = _sortType
        .flatMapLatest { sortType ->
            when(sortType){
                Priority.High -> taskdao.sortbyHighPriority()
                Priority.Medium -> taskdao.sortbyMediumPriority()
                Priority.Low -> taskdao.sortbyLowPriority()
            }
        }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), emptyList())//edge case will only work when the values of tasks are suscribed or caleed or accessed.

     val state = combine(task_state,_sortType,_tasks){ state, sortType, tasks->
        state.copy(
            Task = tasks,
            sortType=sortType
        )
     }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), TaskState())
     fun onTaskEvent(event: TaskEvent){
        when(event){
            is TaskEvent.SaveContent -> {
                val title = state.value.title // taking value of fields for dialog box from the changed flow
                val desc = state.value.desc
                val priority = state.value.priority

                if(title.isBlank()||desc.isBlank()){//edge case if any field in dialog box is empty then it will not add the task in the list
                    return
                }
                val task = tasks(
                    title=title,//adding values in the dialog box
                    description = desc,
                    priority = priority
                )
                viewModelScope.launch {
                    taskdao.upsertTask(task)
                }
                task_state.update { it.copy(
                    isAddingTask = false,//after adding the task update value of each field in the dialog box to initial value
                    title ="",//yani jo cheeze change horhi hai jinka state change horha hai unko combine kara aur
                    desc = "",//fir unko observe krne mein daaldiya ki agar koi si bhi change hi to uske according process hojaye apne ap.
                    priority = Priority.Low
                )
                }
            }
            is TaskEvent.DeleteTask -> {
                viewModelScope.launch {
                    taskdao.deletetask(event.task)
                }
            }
            is TaskEvent.HideDialog -> {
                task_state.update {it.copy(
                    isAddingTask = false
                )
                }
            }
            is TaskEvent.SetDesc -> {
                task_state.update { it.copy(
                    desc = event.description
                ) }
            }
            is TaskEvent.SetPrority ->{
                task_state.update { it.copy(
                    priority = event.priority// this here state is public so that the ui can access it aur fir apn ne teeno cheeze
                ) }
            }
            is TaskEvent.SetTitle -> {
                task_state.update { it.copy(
                    title = event.title
                ) }
            }
            is TaskEvent.ShowDialog -> {
                task_state.update {it.copy(
                    isAddingTask = true
                )
                }
            }
            is TaskEvent.SortTaskPriority -> {
                _sortType.value = event.sortType
            }
//            TaskEvent.IsTaskDone -> {
//                task_state.update { it.copy(
//                    isTaskDone = true
//                ) }
//            }
//            TaskEvent.IsTaskUndone -> {
//                task_state.update { it.copy(
//                    isTaskDone = false
//                ) }
//            }
        }
    }
    //task manager logic ends

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    //password manager logic starts


    private val _password_state = MutableStateFlow(PasswordState())
    private val _sortTypePassword = MutableStateFlow(SortTypePasswords.A_to_Z)

    private val _password = _sortTypePassword
        .flatMapLatest { sortType ->
            when(sortType){
                SortTypePasswords.A_to_Z -> passwordDao.getOrderedAtoZ()
                SortTypePasswords.Z_to_A -> passwordDao.getOrderedZtoA()
            }
        }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), emptyList())

    val password_State = combine(_password_state,_sortTypePassword,_password){ state, sortType, password->
        state.copy(
            passwords = password,
            sortType = sortType
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), PasswordState())


    fun onPassEvent(event : PasswordEvent){
        when(event){
            PasswordEvent.SavePassword -> {
                val platform_name = password_State.value.platform_name
                val username = password_State.value.username
                val password = password_State.value.password

                if(username.isBlank()||password.isBlank()||platform_name.isBlank()||(password.length<8)){//edge case if any field in dialog box is empty then it will not add the task in the list
                    return
                }
                val password1 = Passwords(
                    platform_name = platform_name,
                    username =username,
                    password = password
                )
                viewModelScope.launch {
                    passwordDao.upsertPassword(password1)
                }
                _password_state.update {it.copy(
                    isAddingPassword = false,
                    platform_name = "",
                    username = "",
                    password = "",
                    isGeneratingPass = false
                )

                }
            }
            is PasswordEvent.DeletePassword -> {
                viewModelScope.launch {
                    passwordDao.deletePassword(event.password)
                }
            }
            PasswordEvent.HideDialog -> {
                _password_state.update { it.copy(
                    isAddingPassword = false
                ) }
            }
            PasswordEvent.HideGeneratorDialog -> {
                _password_state.update { it.copy(
                   isGeneratingPass = false
                ) }
            }
            is PasswordEvent.SetPassword -> {
                _password_state.update {it.copy(
                    password = event.password
                )
                }
            }
            is PasswordEvent.SetUsername -> {
                _password_state.update { it.copy(
                    username = event.username
                ) }
            }
            PasswordEvent.ShowDialog -> {
                _password_state.update { it.copy(
                    isAddingPassword = true
                ) }
            }
            PasswordEvent.ShowGeneratorDialog -> {
                _password_state.update { it.copy(
                    isGeneratingPass = true
                ) }
            }
            is PasswordEvent.GeneratePassword -> {
                _password_state.update {it.copy(
                  generatedPass =generatePass()
            )}
            }
            is PasswordEvent.SetPlatform_Name -> {
                _password_state.update { it.copy(
                    platform_name = event.plaform_name
                ) }
            }
            is PasswordEvent.SortPassword -> {
                _sortTypePassword.value =event.sortType
            }
        }
    }


    //password manager logic ends
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    //notes manager logic starts

    private val _note_state = MutableStateFlow(NoteState())
    private val _sortTypeNote = MutableStateFlow(SortTypeNotes.A_to_Z)

    private val _notes = _sortTypeNote
        .flatMapLatest { sortType ->
            when(sortType){
                SortTypeNotes.A_to_Z -> notesDao.getOrderedAtoZ()
                SortTypeNotes.Z_to_A -> notesDao.getOrderedZtoA()
            }
        }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), emptyList())

    val note_State = combine(_note_state,_sortTypeNote,_notes){ state, sortType, notes->
        state.copy(
            notes = notes,
            sortType=sortType
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), NoteState())

    fun onNoteEvent(event : NotesEvent){
        when(event){
            is NotesEvent.DeleteNote -> {
                viewModelScope.launch {
                    notesDao.deleteNote(event.note)
                }
            }
            NotesEvent.HideDialog -> {
                _note_state.update { it.copy(
                    isAddingNote = false
                ) }
            }
            NotesEvent.SaveNote -> {
                val title = note_State.value.title
                val content = note_State.value.content

                if(title.isBlank()||content.isBlank()){//edge case if any field in dialog box is empty then it will not add the task in the list
                    return
                }
                val nottes = Notes(
                    title = title,
                    content = content
                )
                viewModelScope.launch {
                    notesDao.upsertNote(nottes)
                }
                _note_state.update {it.copy(
                    isAddingNote = false,
                    title = "",
                    content = ""
                )
                }
            }
            is NotesEvent.SetContent -> {
                _note_state.update {it.copy(
                    content = event.content
                )
                }
            }
            is NotesEvent.SetTitle -> {
                _note_state.update {it.copy(
                    title = event.title
                )
                }
            }
            NotesEvent.ShowDialog -> {
                _note_state.update { it.copy(
                    isAddingNote = true
                ) }
            }
            is NotesEvent.SortNotes -> {
                _sortTypeNote.value =event.sortType
            }
        }
    }

    //notes manager logic ends


////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //Expense manager logic starts here
    private val _expense_state = MutableStateFlow(ExpenseState())
    private val _expensesortType = MutableStateFlow(SortTypeExpenses.A_to_Z)

    private val _expense = _expensesortType.flatMapLatest { sortType->
        when(sortType){
            SortTypeExpenses.A_to_Z -> expensedao.getOrderedAtoZ()
            SortTypeExpenses.Z_to_A -> expensedao.getOrderedZtoA()
        }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), emptyList())

    val expenseState = combine(_expense_state,_expensesortType,_expense){expensestate,sorttype,expenses->
        expensestate.copy(
            expenses = expenses,
            sortTypeExpenses = sorttype
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), ExpenseState())

    val totalExpense : StateFlow<Int?> = expensedao.getTotalExpense().stateIn(viewModelScope,
        SharingStarted.Eagerly,null)

    val totalfoodcategoryExpense :StateFlow<Int> = expensedao.getfoodCategoryTotal().stateIn(
        viewModelScope,
        SharingStarted.Eagerly,
        0)

    val totalShopcategoryExpense :StateFlow<Int> = expensedao.getshopCategoryTotal().stateIn(
        viewModelScope,
        SharingStarted.Eagerly,
        0)

    val totalTransportcategoryExpense :StateFlow<Int> = expensedao.gettransCategoryTotal().stateIn(
        viewModelScope,
        SharingStarted.Eagerly,
        0)

    val totalEntertainmentcategoryExpense :StateFlow<Int> = expensedao.getenterCategoryTotal().stateIn(
        viewModelScope,
        SharingStarted.Eagerly,
        0)

    val totalOthercategoryExpense :StateFlow<Int> = expensedao.getotherCategoryTotal().stateIn(
        viewModelScope,
        SharingStarted.Eagerly,
        0)

    val restoreBudget :StateFlow<Int> = flow{emit(
        _expense_state.value.Budget
    )}.stateIn(
        viewModelScope,
        SharingStarted.Eagerly,
        0)

    fun onExpenseEvent(event: ExpenseEvent){
        when(event){
            is ExpenseEvent.DeleteExpense -> {
                viewModelScope.launch {
                    expensedao.DeleteExpense(expense = event.expense)
                }
            }
            ExpenseEvent.HideBudgetDialog -> {
                _expense_state.update { it.copy(
                    isAddingBudget = false
                ) }
            }
            ExpenseEvent.SaveExpense -> {
                val price = expenseState.value.price
                val desc = expenseState.value.description
                val month = expenseState.value.month
                val categry = expenseState.value.Category
                    if (price==null){
                        return
                    }
                val expense=expense_table(
                    Price = price,
                    Description = desc,
                    Category = categry,
                    month = month
                )
                viewModelScope.launch {
                    expensedao.AddExpense(expense)
                }
                _expense_state.update {it.copy(
                    price = 0,
                    description = "",
                    month = Month.Jan,
                    Category = category.Food
                )
                }
            }
            is ExpenseEvent.SetCategory -> {
                _expense_state.update { it.copy(
                    Category = event.category
                ) }
            }
            is ExpenseEvent.SetDesc -> {
                _expense_state.update { it.copy(
                    description = event.description
                ) }
            }
            is ExpenseEvent.SetMonth -> {
                _expense_state.update { it.copy(
                    month = event.month
                ) }
            }
            is ExpenseEvent.SetPrice -> {
                _expense_state.update { it.copy(
                    price = event.price
                ) }
            }
            ExpenseEvent.ShowBudgetDialog -> {
                _expense_state.update { it.copy(
                    isAddingBudget = true
                ) }
            }
            is ExpenseEvent.SortExpenses -> {
                _expensesortType.value= event.sortTypeExpenses
            }
            is ExpenseEvent.SetBudget -> {
                val budget = expenseState.value.Budget
                val budgetdb = budget(
                    budgetvalue = budget
                )
                viewModelScope.launch {
                    budgetDao.addBudget(budgetdb)
//                    budgetDao.updateBudget(budgetdb.budgetvalue)
                }
                _expense_state.update { it.copy(
                    Budget = event.budget
                ) }
            }
        }
    }


//expense manager logic ends here
}