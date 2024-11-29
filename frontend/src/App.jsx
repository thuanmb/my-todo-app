import React, { useEffect } from "react";
import { useDispatch, useSelector } from "react-redux";
import TodoList from "./components/TodoList";
import TodoEditModal from "./components/TodoEditModal";
import { fetchTodos } from "./redux/todoSlice";
import "./App.scss";

const App = () => {
	const dispatch = useDispatch();
	const todos = useSelector((state) => state.todos);

	useEffect(() => {
		dispatch(fetchTodos());
	}, [dispatch]);

	return (
		<div className="app">
			<h1>TODO App</h1>
			<TodoList todos={todos.items} />
			<TodoEditModal />
		</div>
	);
};

export default App;
