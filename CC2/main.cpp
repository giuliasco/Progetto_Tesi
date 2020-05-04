#include <iostream>
#include <vector>
#include <stdlib.h>

using namespace std;



   void addEdge(vector<int> adj[], int u, int v){
    adj[u].push_back(v);
    adj[v].push_back(u);
};



int main() {


    int V=1000;
    int k=5;
    vector<int> adj[V];
    int c[V];
    addEdge(adj, 0, 1);

    addEdge(adj, 0, 4);
    addEdge(adj, 1, 2);
    addEdge(adj, 1, 3);
    addEdge(adj, 1, 4);
    addEdge(adj, 2, 3);
    addEdge(adj, 3, 4);

    /*per la colorazione mi creo un vettore color di interi
     * suppongo che ad ogni intero corrisponde un colore differente e suppongo di avere k colori;
     * in questo modo assegno ad ogni vertice v in V un intero/colore differente in maniera totalmente random
     */
    for(int v=0;v<V; v++){
        c[v]=rand() % k+1;
        cout<<c[v]<<endl;
    }



}






