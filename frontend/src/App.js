import React, {Component} from 'react';
import './App.css';
import Registration from "./auth/Registration";
import 'bootstrap/dist/css/bootstrap.min.css';
import { BrowserRouter, Switch, Route } from "react-router-dom";
import Home from "./Home";
import Login from "./auth/Login";

class App extends Component {

	constructor(props) {
		super(props);
	}

	componentDidMount() {
		const username = localStorage.getItem("username");
	}

	render() {
		return (
			<div className="App">
				<BrowserRouter>
					<Switch>
						<Route
							exact
							path={"/"}
							render={props => (
								<Home
									isLoggedIn={false}
									{...props}
									// handleLogin={this.handleLogin}
									// handleLogout={this.handleLogout}
									// loggedInStatus={this.state.loggedInStatus}
								/>
							)}
						/>
						<Route
							exact
							path={"/register"}
							render={props => (
								<Registration
									{...props}
									// loggedInStatus={this.state.loggedInStatus}
								/>
							)}
						/>
						<Route
							exact
							path={"/login"}
							render={props => (
								<Login
									{...props}
									// loggedInStatus={this.state.loggedInStatus}
								/>
							)}
						/>
					</Switch>
				</BrowserRouter>
			</div>
		);
	}
}

export default App;

