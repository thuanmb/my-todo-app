import React from "react";
import { useDispatch } from "react-redux";
import { useNavigate } from "react-router-dom";
import { Box, Grid } from '@mui/material';

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
		<Box sx={{ flex: 1, p: 3 }}>
			<Grid container spacing={2}>
				{todos.map((todo) => (
					<Grid item xs={12} sm={6} md={4} lg={3} key={todo.id}>
						<TodoItem todo={todo} />
					</Grid>
				))}
			</Grid>
		</Box>
	);
};

export default TodoList;
