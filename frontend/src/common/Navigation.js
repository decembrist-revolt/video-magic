import React, {Component} from "react";
import Navbar from "react-bootstrap/Navbar";
import Row from "react-bootstrap/Row";
import Col from "react-bootstrap/Col";
import Container from "react-bootstrap/Container";
import Nav from "react-bootstrap/Nav";

export default class Navigation extends Component {

	constructor(props) {
		super(props);
	}

	render() {
		return (
			<Navbar>
				<Navbar.Brand href="#home">Navbar with text</Navbar.Brand>
				<Navbar.Toggle/>
				<Navbar.Collapse className="justify-content-end">
					<Nav>
						{!this.props.username ? null : (
							<Navbar.Text>
								Signed in as: {this.props.username}
							</Navbar.Text>
						)}
						{this.props.isLoggedIn ? null : (
							<Nav.Link href="/login">Login</Nav.Link>
						)}
						{!this.props.isLoggedIn ? null : (
							<Nav.Link href="/logout">Logout</Nav.Link>
						)}
						{this.props.isLoggedIn ? null : (
							<Nav.Link href="/register">Registration</Nav.Link>
						)}
					</Nav>
				</Navbar.Collapse>
			</Navbar>
		);
	}

}
