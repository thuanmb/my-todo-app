import React, { useState, useEffect } from "react";
import { useDispatch, useSelector } from "react-redux";
import Button from '@mui/material/Button';
import TextField from '@mui/material/TextField';
import Dialog from '@mui/material/Dialog';
import DialogActions from '@mui/material/DialogActions';
import DialogContent from '@mui/material/DialogContent';
import DialogTitle from '@mui/material/DialogTitle';
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

	const handleClose = () => {
		dispatch(closeEditModal());
	};

	return (
			<Dialog
        open={isOpen}
        onClose={handleClose}
        PaperProps={{
          component: 'form',
          onSubmit: handleSave,
        }}
      >
        <DialogTitle>Edit TODO</DialogTitle>
        <DialogContent>
          <TextField
            autoFocus
            required
            margin="dense"
            id="title"
            name="title"
            label="Title"
            type="text"
            fullWidth
            variant="standard"
						value={title}
						onChange={(e) => setTitle(e.target.value)}
          />
          <TextField
						multiline
            autoFocus
            required
            margin="dense"
            id="description"
            name="description"
            label="Description"
            type="text"
            fullWidth
            variant="standard"
						value={description}
						onChange={(e) => setDescription(e.target.value)}
          />
        </DialogContent>
        <DialogActions>
          <Button onClick={handleClose}>Cancel</Button>
          <Button type="submit">Save</Button>
        </DialogActions>
      </Dialog>
	);
};

export default TodoEditModal;
