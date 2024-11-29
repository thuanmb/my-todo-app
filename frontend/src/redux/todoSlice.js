import { createSlice, createAsyncThunk } from "@reduxjs/toolkit";
import { fetchTodosApi } from "../services/api";

export const fetchTodos = createAsyncThunk("todos/fetchTodos", async () => {
	return await fetchTodosApi();
});

const todoSlice = createSlice({
	name: "todos",
	initialState: {
		items: [],
		editModal: {
			isOpen: false,
			currentTodo: null,
		},
	},
	reducers: {
		openEditModal: (state, action) => {
			state.editModal.isOpen = true;
			state.editModal.currentTodo = action.payload;
		},
		closeEditModal: (state, action) => {
			state.editModal.isOpen = false;
			state.editModal.currentTodo = null;
		},
		updateTodo: (state, action) => {
			const { id, title, description } = action.payload;
			const todo = state.items.find((t) => t.id === id);
			if (todo) {
				todo.title = title;
				todo.description = description;
			}
		},
	},
	extraReducers: (builder) => {
		builder.addCase(fetchTodos.fulfilled, (state, action) => {
			state.items = action.payload;
		});
	},
});

export const { openEditModal, closeEditModal, updateTodo } = todoSlice.actions;
export default todoSlice.reducer;
