import React from "react";
import { BrowserRouter as Router, Routes, Route, Navigate } from "react-router-dom";
import { CssBaseline, ThemeProvider, createTheme } from "@mui/material";

import LoginPage from "./pages/LoginPage";
import TodosPage from "./pages/TodosPage";

import PrivateRoute from "./components/PrivateRoute";

import "./App.scss";

const App = () => {
	// MUI Theme
	const theme = createTheme({
		palette: {
			mode: "light",
			primary: { main: "#1976d2" },
			secondary: { main: "#f50057" },
		},
	});

	return (
		<ThemeProvider theme={theme}>
			<CssBaseline />
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
		</ThemeProvider>
	);
};

export default App;
