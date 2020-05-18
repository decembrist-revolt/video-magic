import {Component} from "react";
import React from 'react';
import Container from "react-bootstrap/Container";
import Row from "react-bootstrap/Row";
import Col from "react-bootstrap/Col";

export default class Home extends Component {

	constructor(props) {
		super(props);
	}

	render() {
		return (
			<Container>
				<Row>
					<Col>
						<p>Home</p>
					</Col>
				</Row>
				<Row>
					<Col>
						<p>Hello </p>
					</Col>
				</Row>
			</Container>
		);
	}
}
