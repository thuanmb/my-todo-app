import React from "react";
import { AppBar, Toolbar, Box, Avatar } from "@mui/material";

import SearchBox from "../../components/SearchBox";

const Topbar = () => {
	return (
		<AppBar position="static" color="default">
			<Toolbar>
				<SearchBox />
				<Box sx={{ flexGrow: 1 }} />
				<Avatar alt="Thuan Bui" src="/avatar.png" />
			</Toolbar>
		</AppBar>
	);
};

export default Topbar;
