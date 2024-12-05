import React from "react";
import { Drawer, Box, List, ListItem, ListItemButton, ListItemText, Typography } from "@mui/material";

const labels = ["Work", "Personal", "Urgent", "Completed"];

const Navbar = () => {
	const setFilterLabel = (label) => {
		console.log("selected label: ", label);
	};
	return (
		<Drawer
			variant="permanent"
			sx={{
				width: 240,
				flexShrink: 0,
				[`& .MuiDrawer-paper`]: { width: 240, boxSizing: "border-box" },
			}}
		>
			<Box sx={{ p: 2 }}>
				<Typography variant="h6">Filter by Labels</Typography>
			</Box>
			<List>
				{labels.map((label) => (
					<ListItem key={label} disablePadding>
						<ListItemButton onClick={() => setFilterLabel(label)}>
							<ListItemText primary={label} />
						</ListItemButton>
					</ListItem>
				))}
			</List>
		</Drawer>
	);
};

export default Navbar;
