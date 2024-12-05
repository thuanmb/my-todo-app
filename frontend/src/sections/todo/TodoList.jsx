import React from "react";
import { useDispatch } from "react-redux";
import { useNavigate } from "react-router-dom";

import { logout } from "../../redux/authSlice";
import TodoItem from "./TodoItem";
import "./TodoList.scss";

const TodoList = ({ todos }) => {
	const dispatch = useDispatch();
	const navigate = useNavigate();

	const handleLogout = (e) => {
		dispatch(logout());
		navigate("/login");
	};
	return (
		<div className="todo-list">
			{todos.map((todo) => (
				<TodoItem key={todo.id} todo={todo} />
			))}
			<button onClick={handleLogout}>Logout</button>
		</div>
	);
};

export default TodoList;
