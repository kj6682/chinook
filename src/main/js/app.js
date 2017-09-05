'use strict';

const React = require('react');
const ReactDOM = require('react-dom')
const client = require('./client');

class App extends React.Component {

    constructor(props) {
        super(props);
        this.state = {hops: []};
    }

    componentDidMount() {
        client({method: 'GET', path: '/api/hops'}).done(response => {
            this.setState({hops: response.entity._embedded.hops});
    });
    }

    render() {
        return (
            <HopList hops={this.state.hops}/>
    )
    }
}

class HopList extends React.Component{
    render() {
        var hops = this.props.hops.map(hop =>
            <Hop key={hop._links.self.href} hop={hop}/>
    );
        return (
            <table>
            <tbody>
            <tr>
            <th>Title</th>
            <th>Author</th>
            <th>Loc.</th>
            <th>Type</th>
        </tr>
        {hops}
        </tbody>
        </table>
    )
    }
}
class Hop extends React.Component{
    render() {
        return (
            <tr>
            <td>{this.props.hop.title}</td>
            <td>{this.props.hop.author}</td>
            <td>{this.props.hop.location}</td>
            <td>{this.props.hop.type}</td>
        </tr>
    )
    }
}

ReactDOM.render( <App />, document.getElementById('react'))
