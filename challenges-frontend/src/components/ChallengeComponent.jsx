import * as React from "react";
import ChallengesApiClient from "../services/ChallengesApiClient.ts";
import LastAttemptsComponent from "./LastAttemptsComponent";
import LeaderBoardComponent from "./LeaderBoardComponent";
class ChallengeComponent extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            a: '', b: '',
            user: '',
            message: '',
            guess: 0,
            lastAttempts: []
        };
        this.handleSubmitResult = this.handleSubmitResult.bind(this);
        this.handleChange = this.handleChange.bind(this);
    }
    componentDidMount() {
        ChallengesApiClient.challenge().then(
            res => {
                if (res.ok) {
                    res.json().then(json => {
                        this.setState({
                            a: json.factorA,
                            b: json.factorB
                        });
                    });
                } else {
                    this.updateMessage("Can't reach the server");
                }
            }
        );
    }
    handleChange(event) {
        const name = event.target.name;
        this.setState({
            [name]: event.target.value
        });
    }
    handleSubmitResult(event) {
        event.preventDefault();
        ChallengesApiClient.sendGuess(this.state.user, this.state.a, this.state.b, this.state.guess)
            .then(res => {
                if (res.ok) {
                    res.json().then(json => {
                        if (json.correct) {
                            this.updateMessage("Congratulations! Your guess is correct");
                        } else {
                            this.updateMessage("Oops! Your guess " + json.resultAttempt + " is wrong, but keep playing!");
                        }
                        this.updateLastAttempts(this.state.user);
                        this.refreshChallenge();
                    });
                } else {
                    this.updateMessage("Error: server error or not available");
                }
            });
    }
    updateLastAttempts(userAlias) {
        ChallengesApiClient.getAttempts(userAlias).then(res => {
            if (res.ok) {
                let attempts = [];
                res.json().then(data => {
                    data.forEach(element => {
                        attempts.push(element);
                    });
                    this.setState({
                        lastAttempts: attempts
                    });
                })
            }
        });
    }
    refreshChallenge(){
        ChallengesApiClient.challenge().then(
            res => {
                if (res.ok) {
                    res.json().then(json => {
                        this.setState({
                            a: json.factorA,
                            b: json.factorB,
                            guess: 0
                        });
                    });
                } else {
                    this.updateMessage("Can't reach the server");
                }
            }
        );
    }
    updateMessage(m) {
        this.setState({
            message: m
        });
    }
    render() {
        return (
            <div className="display-column">
                <div>
                    <h3>Your new challenge is</h3>
                    <div className="challenge">
                        {this.state.a} x {this.state.b}
                    </div>
                </div>
                <form onSubmit={this.handleSubmitResult}>
                    <label>
                        Your alias:
                        <input type="text" maxLength="12"
                            name="user"
                            value={this.state.user}
                            onChange={this.handleChange} />
                    </label>
                    <br />
                    <label>
                        Your guess:
                        <input type="number" min="0"
                            name="guess"
                            value={this.state.guess}
                            onChange={this.handleChange} />
                    </label>
                    <br />
                    <input type="submit" value="Submit" />
                </form>
                <h4>{this.state.message}</h4>
                {this.state.lastAttempts.length > 0 &&
                    <LastAttemptsComponent lastAttempts={this.state.
                        lastAttempts} />
                }
                <LeaderBoardComponent />
            </div>
        );
    }
}

export default ChallengeComponent;