import React, {Component} from 'react';
import './App.css';
import Registration from "./auth/Registration";
import 'bootstrap/dist/css/bootstrap.min.css';
import {BrowserRouter, Switch, Route} from "react-router-dom";
import Home from "./Home";
import Login from "./auth/Login";
import Navigation from "./common/Navigation";

class App extends Component {

	constructor(props) {
		super(props);
		this.state = {
			username: '',
			isLoggedIn: false
		};
	}

	componentDidMount() {
		this.ifLoggedIn()
	}

	async ifLoggedIn() {
		const username = localStorage.getItem("username");
		if (username) {
			try {
				let response = await fetch("/user/whoami");
				if (response.status === 200) {
					let json = await response.json()
					console.log("LOGGED IN FROM STORAGE");
					this.setState({
						username: json.username,
						isLoggedIn: true
					})
				} else {
					localStorage.setItem("username", "");
				}
			} catch (err) {
				console.log('error', err)
			}
		}
	}

	render() {
		return (
			<div className="App">
				<Navigation username={this.state.username}
							isLoggedIn={this.state.isLoggedIn}/>
				<BrowserRouter>
					<Switch>
						<Route
							exact
							path={"/"}
							render={props => (
								<Home
									isLoggedIn={this.state.isLoggedIn}
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
									onLoggedIn={this.ifLoggedIn}
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

