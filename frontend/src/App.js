import React, {Component} from 'react';
import logo from './logo.svg';
import './App.css';

function App() {
	return (
		<div className="App">
			<header className="App-header">
				<img src={logo} className="App-logo" alt="logo"/>
				<p>
					Edit <code>src/App.js</code> and save to reload.
				</p>
				<a
					className="App-link"
					href="https://reactjs.org"
					target="_blank"
					rel="noopener noreferrer"
				>
					Learn React
				</a>
			</header>
			<Register/>
		</div>
	);
}

class Register extends Component {

	constructor(props) {
		super(props);
		this.state = {
			username: '',
			password: '',
			message: ''
		};
		this.handleChange = this.handleChange.bind(this);
		this.handleRegisterClick = this.handleRegisterClick.bind(this);
	}

	handleChange(event) {
		this.setState({
			[event.target.name]: event.target.value
		});
	}

	handleRegisterClick(event) {
		console.log('Try to register', this.state.username, this.state.password);
		const body = {
			username: this.state.username,
			password: this.state.password
		};
		fetch('/user/register',{
			method: 'POST',
			body: JSON.stringify(body),
			headers: {
				'Content-Type': 'application/json'
			}
		});
		this.setState({
			message: '123'
		});
		event.preventDefault();
	}

	render() {
		return (
			<form>
				<label>
					Username:
					<input type="text" name="username" value={this.state.username}
						   onChange={this.handleChange}/>
				</label>
				<label>
					Password:
					<input type="text" name="password" value={this.state.password}
						   onChange={this.handleChange}/>
				</label>
				<button onClick={this.handleRegisterClick}>Register</button>
				{this.state.message ? <p>OK</p> : <p>MESSAGE</p>}
			</form>
		);
	}

}

export default App;
