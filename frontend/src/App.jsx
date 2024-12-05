import React from "react";
import { BrowserRouter as Router, Routes, Route, Navigate } from "react-router-dom";

import LoginPage from "./pages/LoginPage";
import TodosPage from "./pages/TodosPage";

import PrivateRoute from "./components/PrivateRoute";

import "./App.scss";

const App = () => {
	return (
		<Router>
			<Routes>
				<Route path="/" element={<Navigate to="/login" />} />
				<Route path="/login" element={<LoginPage />} />
				<Route
					path="/todos"
					element={
						<PrivateRoute>
							<TodosPage />
						</PrivateRoute>
					}
				/>
			</Routes>
		</Router>
	);
};

export default App;
