import * as React from 'react';
import GameApiClient from '../services/GameApiClient.ts';
import ChallengesApiClient from '../services/ChallengesApiClient.ts';
class LeaderBoardComponent extends React.Component {
    constructor(props){
        super(props);
        this.state = {
            leaderboard: [],
            serverError: false
        }
    }
    componentDidMount(){
        this.refreshLeaderBoard();
        // sets a timer to refresh the leaderboard every 5 seconds
        setInterval(this.refreshLeaderBoard.bind(this), 5000);
    }
    getLeaderBoardData() {
        return GameApiClient.leaderBoard().then(res => {
            if (res.ok){
                return res.json();
            } else {
                return Promise.reject("Gamification: error response");
            }
        });
    }
    getUserAliasData(userIds) {
        return ChallengesApiClient.getUsers(userIds).then(res => {
            if (res.ok){
                return res.json();
            } else {
                return Promise.reject("Multiplication: error response");
            }
        });
    }
    updateLeaderBoard(lb){
        this.setState({
            leaderboard: lb,
            // reset the flag
            serverError: false
        });
    }
    refreshLeaderBoard(){
        this.getLeaderBoardData().then(lbData => {
            let userIds = lbData.map(row => row.userId);
            this.getUserAliasData(userIds).then(userData => {
                // build a map of id -> alias
                let userMap = new Map();
                userData.forEach(user => {
                    userMap.set(user.id, user.alias);
                });
                // add a property to existing lb data
                lbData.forEach(row => row['alias'] = userMap.get(row.userId));
                this.updateLeaderBoard(lbData);
            }).catch(reason => {
                console.log('Error mapping user ids', reason);
                this.updateLeaderBoard(lbData);
            });
        }).catch(reason => {
            this.setState({ serverError: true });
            console.log('Gamification server error', reason);
        });
    }
    render(){
        if (this.state.serverError){
            return (<div>We're sorry, but we can't display game statistics at this moment.</div>);
        }
        return (
            <div>
                <h3>Leaderboard</h3>
                <table>
                    <thead>
                        <tr>
                            <th>User</th>
                            <th>Score</th>
                            <th>Badges</th>
                        </tr>
                    </thead>
                    <tbody>
                        {this.state.leaderboard.map(row => <tr key={row.userId}>
                            <td>{row.alias ? row.alias : row.userId}</td>
                            <td>{row.totalScore}</td>
                            <td>{row.badges.map(b => <span className='badge' key={b}>{b}</span>)}</td>
                        </tr>)}
                    </tbody>
                </table>
            </div>
        );
    }
}

export default LeaderBoardComponent;