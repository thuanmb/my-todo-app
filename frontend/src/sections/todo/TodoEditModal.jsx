import React, { useState, useEffect } from "react";
import { useDispatch, useSelector } from "react-redux";
import { updateTodo, closeEditModal } from "../../redux/todoSlice";

const TodoEditModal = () => {
	const { isOpen, currentTodo } = useSelector((state) => state.todos.editModal);
	const dispatch = useDispatch();
	console.log(currentTodo);
	const [title, setTitle] = useState(currentTodo?.title || "");
	const [description, setDescription] = useState(currentTodo?.description || "");

	useEffect(() => {
		setTitle(currentTodo?.title || "");
		setDescription(currentTodo?.description || "");
	}, [currentTodo]);

	if (!isOpen) {
		return null;
	}

	const handleSave = () => {
		dispatch(updateTodo({ id: currentTodo.id, title, description }));
		dispatch(closeEditModal());
	};

	return (
		<div className="edit-modal">
			<div className="modal-content"></div>
			<h2>Edit TODO</h2>
			<input type="text" value={title} onChange={(e) => setTitle(e.target.value)} placeholder="Title" />
			<textarea value={description} onChange={(e) => setDescription(e.target.value)} placeholder="Description" />
			<button onClick={handleSave}>Save</button>
			<button onClick={() => dispatch(closeEditModal())}>Cancel</button>
		</div>
	);
};

export default TodoEditModal;
