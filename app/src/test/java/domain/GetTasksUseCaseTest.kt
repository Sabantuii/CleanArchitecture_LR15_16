package domain

import com.example.lr_15_16_jetcom.FakeTaskRepository
import com.example.lr_15_16_jetcom.domain.model.Task
import com.example.lr_15_16_jetcom.domain.usecase.GetTasksUseCase
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class GetTasksUseCaseTest {
    private lateinit var getTasksUseCase: GetTasksUseCase
    private lateinit var repository: FakeTaskRepository

    @Before
    fun setUp() {
        repository = FakeTaskRepository()
        getTasksUseCase = GetTasksUseCase(repository)
    }

    @Test
    fun `should return tasks from repository`() = runTest {
        // Given: добавляем задачу в фейк
        val task = Task(id = 1, title = "Test Task", isCompleted = false, createdAt = 0)
        repository.addTask(task)

        // When: вызываем UseCase
        val result = getTasksUseCase().getOrNull()

        // Then: проверяем, что вернулась именно наша задача
        assertEquals(1, result?.size)
        assertEquals("Test Task", result?.get(0)?.title)
    }
}