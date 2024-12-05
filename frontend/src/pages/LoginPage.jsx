import React, { useState } from "react";
import { useDispatch } from "react-redux";
import { useNavigate } from "react-router-dom";
import Button from "@mui/material/Button";

import { login } from "../redux/authSlice";

import "./LoginPage.scss";

const LoginPage = () => {
	const [username, setUsername] = useState("");
	const [password, setPassword] = useState("");
	const dispatch = useDispatch();
	const navigate = useNavigate();

	const handleSubmit = async (e) => {
		e.preventDefault();
		const success = await dispatch(login({ username, password })).unwrap();
		if (success) {
			navigate("/todos");
		} else {
			alert("Invalid credentials");
		}
	};
	return (
		<div className="login-page">
			<form onSubmit={handleSubmit}>
				<h2>Login</h2>
				<input type="text" placeholder="User Name" value={username} onChange={(e) => setUsername(e.target.value)} />
				<input type="password" placeholder="Password" value={password} onChange={(e) => setPassword(e.target.value)} />
				<Button variant="contained" type="submit">
					Login
				</Button>
			</form>
		</div>
	);
};

export default LoginPage;
