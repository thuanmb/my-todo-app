import React from "react";
import { Box } from "@mui/material";

import Navbar from "../sections/common/Navbar";
import Topbar from "../sections/common/Topbar";

const MainLayout = ({ children }) => {
	return (
		<Box sx={{ display: "flex", height: "100vh" }}>
			<Navbar />
			<Box sx={{ flex: 1, display: "flex", flexDirection: "column" }}>
				<Topbar />
				{children}
			</Box>
		</Box>
	);
};

export default MainLayout;
