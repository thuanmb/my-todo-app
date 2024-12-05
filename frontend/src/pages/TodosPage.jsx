import React, { useEffect } from "react";
import { useDispatch, useSelector } from "react-redux";

import MainLayout from "../layout/MainLayout";
import TodoList from "../sections/todo/TodoList";
import TodoEditModal from "../sections/todo/TodoEditModal";

import { fetchTodos } from "../redux/todoSlice";

const TodosPage = () => {
	const dispatch = useDispatch();
	const todos = useSelector((state) => state.todos);

	useEffect(() => {
		dispatch(fetchTodos());
	}, [dispatch]);

	return (
		<MainLayout>
			<TodoList todos={todos.items} />
			<TodoEditModal />
		</MainLayout>
	);
};

export default TodosPage;
