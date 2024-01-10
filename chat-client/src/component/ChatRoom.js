import React, { useState, useEffect } from 'react'
import { over } from 'stompjs'
import SockJS from 'sockjs-client'
import { Helmet } from 'react-helmet';
import './ChatRoom.css';
import axios from 'axios';
import Cookies from "universal-cookie";
import { jwtDecode } from "jwt-decode";
import { useNavigate } from 'react-router-dom'


var stompClient = null;
const cookies = new Cookies();
const ChatRoom = () => {
    const navigate = useNavigate();
    const [token, setToken] = useState(cookies.get("jwt_token"));
    const [publicChats, setPublicChats] = useState([]);
    const [privateChats, setPrivateChats] = useState([]);
    const [tabs, setTabs] = useState([]);




    var [currentTabs, setCurrentTabs] = useState(
        {
            id: "",
            name: ""
        }
    );
    const [searchText, setSearchText] = useState('');
    const [searchResult, setSearchResult] = useState('');



    const [userData, setUserData] = useState({
        username:token && jwtDecode(token).sub,
        recievername: "",
        connected: false,
        message: ""
    });



    const [activeTab, setActiveTab] = useState('private');



    useEffect(() => {
        const fetchData = async () => {
            try {
                const jwt = await cookies.get("jwt_token");
                if (!jwt) {
                    console.error("Token not found in cookies");
                    navigate("/");
                } else {
                    const decoded = jwtDecode(jwt);
                    console.log(decoded);
                    setUserData({
                        username: decoded.sub
                    });




                }
            } catch (error) {
                console.error("Error retrieving token:", error);
            }
        };
    fetchData();
    registerUser();
      
    }, []);




    useEffect(() => {
        console.log("call use efect 2")

        const loadAllChatsAndGroups = async () => {
            try {

                if (activeTab === 'private') {
                    const response = await axios.get('http://localhost:9000/chat/' + userData.username, {
                        headers: {
                            Authorization: `Bearer ${token}`,
                        },
                    });


                    setTabs(response.data);
                } else {
                    const response = await axios.get('http://localhost:9000/group/members/' + userData.username, {
                        headers: {
                            Authorization: `Bearer ${token}`,
                        },
                    });


                    setTabs(response.data);
                }



            } catch (error) {
                console.log(error);
            }

        };
        loadAllChatsAndGroups();
    }, [activeTab]);


    useEffect(() => {
        console.log("call use efect 3")
        const fetchData = async () => {
            try {

                if (activeTab === 'private') {
                    const response = await axios.get('http://localhost:9000/chat/messages/' + currentTabs.id, {
                        headers: {
                            Authorization: `Bearer ${token}`,
                        },
                    });
                    setPrivateChats(response.data);



                } else {
                    const response = await axios.get('http://localhost:9000/group/messages/' + currentTabs.id, {
                        headers: {
                            Authorization: `Bearer ${token}`,
                        },
                    });


                    setPublicChats(response.data);
                }



            } catch (error) {
                console.log(error);
            }
        };
        fetchData();
    }, [currentTabs]);


    const handleUsername = (event) => {
        const { value } = event.target;
        setUserData({ ...userData, "username": value })

    }

    const registerUser = (event) => {
        var headers = {
            Authorization: `Bearer ${token}`
        };

        let socket = new SockJS('http://localhost:9000/ws');

        stompClient = over(socket);

        stompClient.connect(headers, onConnected, onError);
    }

    const onConnected = (frame) => {

        stompClient.subscribe('/chatroom/public', messageReceived)
        stompClient.subscribe("/user/" + userData.username + "/message", privateMessagedRecived);

    }



    const onError = (err) => {
        console.log(err);
    }

    const messageReceived = (payload) => {
        console.log("call messageReceived function ");
        let payloadData = JSON.parse(payload.body);
        setPublicChats(prevChats => {
            if (Array.isArray(prevChats)) {
                return prevChats.concat(payloadData);
            } else {
                console.log("bug roi dit me may");
            }
        });
    }

    const privateMessagedRecived = (payload) => {
        // console.log("call privateMessagedRecived function ");
        let payloadData = JSON.parse(payload.body);
        setPrivateChats(prevChats => {
            if (Array.isArray(prevChats)) {
                return prevChats.concat(payloadData);
            } else {
                console.log("bug roi dit me may");
            }
        });

    }

    function sendValue() {

        var chatMessage = {
            groupId: "6582eeb378138448732c2cd8",
            senderId: userData.username,
            content: userData.message,

        };

        var headers = {
            Authorization: `Bearer ${token}`
        };

        stompClient.send("/app/group", headers, JSON.stringify(chatMessage));

        setUserData({ ...userData, message: "" });



    }


    function sendPrivateChat() {
        console.log("send private chat to " + '/user/' + userData.username + '/message');

        var chatMessage = {
            sender: userData.username,
            receiver: currentTabs.name,
            content: userData.message,
            chatId: currentTabs.id
        }

        var headers = {
            Authorization: `Bearer ${token}`
        };

        stompClient.send("/app/chat", headers, JSON.stringify(chatMessage));

        setPrivateChats(prevChats => {
            if (Array.isArray(prevChats)) {
                return prevChats.concat(chatMessage);
            } else {
                console.log("bug roi dit me may");
            }
        });

        setUserData({ ...userData, message: "" });
    }

    const handleMessage = (event) => {
        const { value } = event.target;
        setUserData({ ...userData, "message": value });
    }

    function handleSearch() {
        try {


            axios.get('http://localhost:9000/user/'+searchText)
                .then((response) => {
                    console.log(response);
                    setSearchResult(response);
                 }, (error) => {
                    console.log(error);
                });




        } catch (error) {
            console.log(error);
        }
    }


    const onTabClicked = (tab) => {
        setActiveTab(tab);
        setPrivateChats(null);
        setPublicChats(null);
        setCurrentTabs(null);


    }

    const onCurrentComponentCLicked = (id, name) => {
        console.log("call onCurrentComponentCLicked");
        setCurrentTabs({
            id: id,
            name: name
        });



    }



    const handleContainerClick = (event) => {

        if (event.target.className !== 'search-result clearfix') {
            setSearchResult(null);
        }
    };



    return (


        <div className="container" onClick={handleContainerClick}>
            <Helmet>
                <link
                    href="https://cdn.jsdelivr.net/npm/bootstrap@4.5.0/dist/css/bootstrap.min.css"
                    rel="stylesheet"
                />
            </Helmet>
            <div className="row clearfix">
                <div className="col-lg-12">
                    <div className="card chat-app">


                        <div id="plist" className="people-list">
                            <div className="input-group">

                                <input
                                    type="text"
                                    className="form-control"
                                    placeholder="Search..."
                                    value={searchText}
                                    onChange={(e) => setSearchText(e.target.value)}
                                />

                                <div className="input-group-prepend">
                                    <span className="input-group-text"><i className="fa fa-search" onClick={() => handleSearch()}>Search</i></span>
                                </div>

                                {searchResult && searchResult.data  && (
                                    <div className="search-result clearfix">
                                        <div className="about result-about" >
                                        <img src="https://bootdey.com/img/Content/avatar/avatar2.png" alt="avatar" />
                                            <div className="name" >{searchResult.data.username}</div>
                                        </div>
                                    </div>
                                )}
                            </div>

                            <ul className="nav nav-tabs mt-2" id="chatTabs">
                                <li className="nav-item">
                                    <span className={`nav-link ${activeTab === 'private' ? 'active' : ''}`} id="private-tab" data-toggle="tab" onClick={() => onTabClicked('private')}>Private Chat</span>
                                </li>
                                <li className="nav-item">
                                    <span className={`nav-link ${activeTab === 'group' ? 'active' : ''}`} id="group-tab" data-toggle="tab" onClick={() => onTabClicked('group')}>Group Chat</span>
                                </li>
                            </ul>
                            <div className="tab-content">
                                <div className={`tab-pane fade ${activeTab === 'private' ? 'show active' : ''}`} id="privateChat">
                                    <ul className="list-unstyled chat-list mb-0">
                                        {
                                            tabs.map((current, index) => (
                                                <li className="clearfix" key={index} onClick={() => onCurrentComponentCLicked(current.id, userData.username === current.origin ? current.destination : current.origin)}>
                                                    <img src="https://bootdey.com/img/Content/avatar/avatar2.png" alt="avatar" />
                                                    <div className="about" >
                                                        <div className="name" >{userData.username === current.origin ? current.destination : current.origin}</div>
                                                        <div className="status"> <i className="fa fa-circle online"></i> online </div>
                                                    </div>

                                                </li>
                                            )
                                            )
                                        }



                                    </ul>
                                </div>
                                <div className={`tab-pane fade ${activeTab === 'group' ? 'show active' : ''}`} id="groupChat">
                                    <ul className="list-unstyled chat-list mb-0">
                                        {
                                            tabs.map((current, index) => (
                                                <li className="clearfix" key={index} onClick={() => onCurrentComponentCLicked(current.id, current.groupName)}>
                                                    <img src="https://bootdey.com/img/Content/avatar/avatar2.png" alt="avatar" />
                                                    <div className="about" >
                                                        <div className="name" >{current.groupName}</div>
                                                        <div className="status"> <i className="fa fa-circle online"></i> online </div>
                                                    </div>

                                                </li>
                                            )
                                            )
                                        }

                                    </ul>
                                </div>
                            </div>
                        </div>

                        <div className="chat">
                            <div className="chat-header clearfix">
                                <div className="row">
                                    <div className="col-lg-6">
                                        <a href="" data-toggle="modal" data-target="#view_info">
                                            {currentTabs && currentTabs.id && (<img src="https://bootdey.com/img/Content/avatar/avatar2.png" alt="avatar" />)}
                                        </a>
                                        <div className="chat-about">
                                            <h6 className="m-b-0">{currentTabs && currentTabs.name}</h6>
                                        </div>
                                    </div>
                                   
                                </div>
                            </div>
                            <div className="chat-history">
                                <ul className="m-b-0">



                                    {
                                        activeTab === 'private' && currentTabs && Array.isArray(privateChats) && (
                                            privateChats.map((current, index) => (
                                                <li className="clearfix" key={index}>
                                                    <div className="message-data">
                                                        <span className="message-data-time"></span>
                                                    </div>
                                                    <div className={`message ${userData.username === current.sender ? 'other-message float-right' : 'my-message'}`}  >{current.content}</div>
                                                </li>
                                            ))
                                        )
                                    }

                                    {
                                        activeTab === 'group' && currentTabs && Array.isArray(publicChats) && (
                                            publicChats.map((current, index) => (
                                                <li className="clearfix" key={index}>
                                                    <div className={`message-data ${userData.username === current.senderId ? 'text-right' : ''}`}>
                                                        <span className="message-data-time ">{current.senderId}</span>
                                                    </div>
                                                    <div className={`message ${userData.username === current.senderId ? 'other-message float-right' : 'my-message'}`}  >{current.content}</div>

                                                </li>
                                            ))
                                        )
                                    }




                                </ul>
                            </div>


                            {currentTabs && currentTabs.id && (
                                <div className="chat-message clearfix">
                                    <div className="input-group mb-0">
                                        <input type="text" className="form-control" placeholder="Enter text here..." value={userData.message} onChange={handleMessage} />
                                        <div className="input-group-prepend">
                                            {
                                                activeTab === 'group' && (<span className="input-group-text" onClick={sendValue}>Send</span>)
                                            }

                                            {
                                                activeTab === 'private' && (<span className="input-group-text" onClick={sendPrivateChat}>Send</span>)
                                            }
                                        </div>
                                    </div>
                                </div>
                            )}



                        </div>
                    </div>
                </div>
            </div>
        </div>




    )
}

export default ChatRoom






