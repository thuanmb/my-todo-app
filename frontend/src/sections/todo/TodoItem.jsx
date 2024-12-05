import React from "react";
import { useDispatch } from "react-redux";
import { openEditModal } from "../../redux/todoSlice";
import "./TodoItem.scss";

const TodoItem = ({ todo }) => {
	const dispatch = useDispatch();
	const handleOnClick = () => {
		dispatch(openEditModal(todo));
	};
	return (
		<div className="todo-item" onClick={handleOnClick}>
			<h3>{todo.title}</h3>
			<p>{todo.description}</p>
		</div>
	);
};

export default TodoItem;
