#include "./libs/src/sio_client.h"

#include <functional>
#include <iostream>
#include <thread>
#include <mutex>
#include <condition_variable>
#include <string>
#ifdef WIN32

#define HIGHLIGHT(__O__) std::cout<<__O__<<std::endl
#define EM(__O__) std::cout<<__O__<<std::endl
#include <stdio.h>
#include <tchar.h>

#else

#define HIGHLIGHT(__O__) std::cout<<"\e[1;31m"<<__O__<<"\e[0m"<<std::endl
#define EM(__O__) std::cout<<"\e[1;30;1m"<<__O__<<"\e[0m"<<std::endl

#endif

using namespace sio;
using namespace std;

std::mutex _lock;
std::condition_variable_any _cond;
bool connect_finish = false;
string id;
string solve1[10];
string solve2[10];
int step_=0;
bool sw;


class connection_listener
{
    sio::client &handler;

public:

    connection_listener(sio::client& h):
    handler(h)
    {
    }


    void on_connected()
    {
        _lock.lock();
        _cond.notify_all();
        connect_finish = true;
        _lock.unlock();
    }
    void on_close(client::close_reason const& reason)
    {
        std::cout<<"sio closed "<<std::endl;
        exit(0);
    }

    void on_fail()
    {
        std::cout<<"sio failed "<<std::endl;
        exit(0);
    }
};

int participants = -1;

socket::ptr current_socket;

class ServerTaquin{
    //int participants ;
    sio::client h;
    connection_listener l;
    socket::ptr current_socket;
    bool connect_finish_;
    string url="https://proyect-taquin-bermeom.c9users.io:8080";

public:
    
    
    ServerTaquin():l(h){
        participants=-1;
        connect_finish_ = false;
    }
    
    void connectionServer(){
        h.set_open_listener(std::bind(&connection_listener::on_connected, &l));
        h.set_close_listener(std::bind(&connection_listener::on_close, &l,std::placeholders::_1));
        h.set_fail_listener(std::bind(&connection_listener::on_fail, &l));
        h.connect(url);
        _lock.lock();
        if(!connect_finish)
        {
            _cond.wait(_lock);
        }
        _lock.unlock();
        current_socket = h.socket();
    }  
    void loginServer(string &nickname){
            current_socket->on("login", sio::socket::event_listener_aux([&](string const& name, message::ptr const& data, bool isAck,message::list &ack_resp){
                _lock.lock();
                participants = data->get_map()["numUsers"]->get_int();
                id = data->get_map()["id"]->get_string();
                bool plural = participants !=1;
                HIGHLIGHT("Welcome to TAQUIN \nthere"<<(plural?" are ":"'s ")<< participants<<(plural?" participants":" participant")<<"\n Your ID "<<id);//;
                _cond.notify_all();
                _lock.unlock();
                current_socket->off("login");
            }));
            current_socket->emit("add user", nickname);
            _lock.lock();
            if (participants<0) {
                _cond.wait(_lock);
            }
            _lock.unlock();
            bind_events(current_socket);
        
    }
    void bind_events(socket::ptr &socket){
        	current_socket->on("new message", sio::socket::event_listener_aux([&](string const& name, message::ptr const& data, bool isAck,message::list &ack_resp)
                               {
                                   _lock.lock();
                                   string user = data->get_map()["username"]->get_string();
                                   string message = data->get_map()["message"]->get_string();
                                   EM(user<<":"<<message);
                                   _lock.unlock();
                               }));
            
            current_socket->on("start", sio::socket::event_listener_aux([&](string const& name, message::ptr const& data, bool isAck,message::list &ack_resp)
                               {
                                   _lock.lock();
                                    current_socket->emit("start-request", id);
                                   _lock.unlock();
                               }));
            current_socket->on("get-matriz", sio::socket::event_listener_aux([&](string const& name, message::ptr const& data, bool isAck,message::list &ack_resp)
                               {
                                   _lock.lock();
                                   string n_size=data->get_map()["n_size"]->get_string();
                                    int n;
                                    if(n_size.size()==1){
                                        n=n_size[0]-'0';
                                    }else{
                                        n=10;    
                                    }
                                    cout<<n_size<<" "<<n<<endl;
                                    string matriz="0 3 2 1";
                                     if (!sw){
                                            matriz="1 0 3 2";            
                                    }
                                    current_socket->emit("get-matriz-reply", matriz );
                                   _lock.unlock();
                               }));
            current_socket->on("solve", sio::socket::event_listener_aux([&](string const& name, message::ptr const& data, bool isAck,message::list &ack_resp)
                               {
                                    _lock.lock();
                                    string matriz=data->get_map()["matriz"]->get_string();
                                    string id_=data->get_map()["id"]->get_string();
                                    
                                    if(id_==id+""){
                                        cout<<"Solve -> ";
                                        step_=0;
                                        cout<<matriz<<endl;
                                        string s=std::to_string((step_+1));
                                        if (sw){
                                            s+=" "+solve1[step_];
                                            cout<<s<<endl;
                                        }else{
                                            s+=" "+solve2[step_];
                                            cout<<s<<endl;
                                        }
                                        current_socket->emit("step",s);
                                        step_++;
                                    }
                                    //*/
                                    _lock.unlock();
                               }));
           current_socket->on("ack-solve", sio::socket::event_listener_aux([&](string const& name, message::ptr const& data, bool isAck,message::list &ack_resp)
                               {
                                    _lock.lock();
                                    //string matriz=data->get_map()["matriz"]->get_string();
                                    //cout<<matriz<<endl;
                                    cout<<"ack-solve ";
                                    string s=std::to_string((step_+1));
                                    if (sw && step_<3){
                                        s+=" "+solve1[step_];
                                        cout<<"==> "<<s<<endl;
                                        current_socket->emit("step",s);
                                        step_++;
                                    }else if(!sw && step_<6){
                                        s+=" "+solve2[step_];
                                        cout<<"==> "<<s<<endl;
                                        current_socket->emit("step",s);
                                        step_++;
                                    }else{
                                        step_=0;
                                    }
                                    _lock.unlock();
                               }));
           current_socket->on("error-solve", sio::socket::event_listener_aux([&](string const& name, message::ptr const& data, bool isAck,message::list &ack_resp)
                               {
                                    _lock.lock();
                                    //string matriz=data->get_map()["matriz"]->get_string();
                                    //cout<<matriz<<endl;
                                    //current_socket->emit("step", "0 0");
                                    _lock.unlock();
                               }));
        
          
        }
        
    
    connection_listener getConnection_listenert(){
        return l;
    }
    
    socket::ptr getCurrent_socket(){
        return current_socket;
    }
    
    void endConnection(){
        h.sync_close();
        h.clear_con_listeners();
        
    }
    
    
    };


int main(int argc ,const char* args[])
{
    
    solve1[0]="0 0";
    solve1[1]="0 1";
    solve1[2]="1 1";
    
    solve2[0]="0 1";
    solve2[1]="1 1";
    solve2[2]="1 0";
    solve2[3]="0 0";
    solve2[4]="0 1";
    solve2[5]="1 1";
    //solve2[1]="2 1 0"; "0 3 2 1"; 1 0 3 2
    //solve2[2]="3 0 0";
    ServerTaquin st;
    st.connectionServer();

    string nickname;
    while (nickname.length() == 0) {
        HIGHLIGHT("Type your nickname:");
        getline(cin, nickname);
    }
    cin>>sw;
	st.loginServer(nickname);
	current_socket=st.getCurrent_socket();


    HIGHLIGHT("Start to chat,commands:\n'$exit' : exit chat\n");
    for (std::string line; std::getline(std::cin, line);) {
        if(line.length()>0)
        {
            if(line == "$exit")
            {
                break;
            }
            
            current_socket->emit("new message", line);
            _lock.lock();
            EM("\t\t\t"<<line<<":"<<"You");
            _lock.unlock();
        }
    }


    HIGHLIGHT("Closing...");
	st.endConnection();
	return 0;
}

