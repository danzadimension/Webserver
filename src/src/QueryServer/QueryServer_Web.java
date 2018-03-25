package src.QueryServer;

import javax.xml.ws.Endpoint;


public class QueryServer_Web {
	public static void main(String args[]){
		QueryServer web = new QueryServer();
		Endpoint endpoint = Endpoint.publish("http://localhost:8080/query_server", web);
	}
}