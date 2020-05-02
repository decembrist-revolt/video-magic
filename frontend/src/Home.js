import {Component} from "react";
import React from 'react';

export default class Home extends Component {

	constructor(props) {
		super(props);
	}

	render() {
		return (
			<div>
				<p>Hello Jopa</p>
				<a href="/register">Registration</a>
			</div>
		);
	}
}
