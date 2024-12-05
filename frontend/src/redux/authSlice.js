import { createSlice, createAsyncThunk } from "@reduxjs/toolkit";
import { loginApi } from "../services/api";

export const login = createAsyncThunk("auth/login", async (credentials) => {
	const response = await loginApi(credentials);
	if (response.success) {
		return response;
	} else {
		throw new Error("Invalid credentials");
	}
});

const authSlice = createSlice({
	name: "auth",
	initialState: {
		isAuthenticated: false,
		user: null,
	},
	reducers: {
		logout: (state) => {
			state.isAuthenticated = false;
			state.user = null;
		},
	},
	extraReducers: (builder) => {
		builder.addCase(login.fulfilled, (state, action) => {
			state.isAuthenticated = true;
			state.user = action.payload.user;
		});
	},
});

export const { logout } = authSlice.actions;
export default authSlice.reducer;
