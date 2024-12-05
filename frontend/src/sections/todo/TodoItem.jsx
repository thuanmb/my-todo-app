import React from "react";
import { useDispatch } from "react-redux";
import { Card, CardContent, Typography } from "@mui/material";
import { openEditModal } from "../../redux/todoSlice";
import "./TodoItem.scss";

const TodoItem = ({ todo }) => {
	const dispatch = useDispatch();
	const handleOnClick = () => {
		dispatch(openEditModal(todo));
	};
	return (
		<Card sx={{ maxWidth: 345, margin: "1rem" }} onClick={handleOnClick}>
			<CardContent>
				<Typography variant="h6" component="div">
					{todo.title}
				</Typography>
				<Typography variant="body2" color="text.secondary">
					{todo.description}
				</Typography>
				<Typography variant="caption" display="block" sx={{ mt: 1 }}>
					Label: [add label here]
				</Typography>
			</CardContent>
		</Card>
	);
};

export default TodoItem;
