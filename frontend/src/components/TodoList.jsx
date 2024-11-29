import React from "react";
import TodoItem from "./TodoItem";
import "./TodoList.scss";

const TodoList = ({ todos }) => {
	return (
		<div className="todo-list">
			{todos.map((todo) => (
				<TodoItem key={todo.id} todo={todo} />
			))}
		</div>
	);
};

export default TodoList;
