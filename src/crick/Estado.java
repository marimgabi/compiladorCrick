package crick;

import java.util.ArrayList;
import java.util.List;

public class Estado {
    public String NTerminal;
    public List<List<String>> producoes = new ArrayList<>();
    public List<String> first = new ArrayList<>();
    public List<String> follow = new ArrayList<>();

    public Estado() {
    }

    public void printProd(){
        System.out.println("Não terminal: "+NTerminal);
        for(List<String> producao: producoes){
            String prod="";
            for(String a : producao){
                prod=prod+a+' ';
            }
            System.out.println(prod);
        }
    }
    public void printFF(){
        System.out.println("Não Terminal: "+NTerminal);
        System.out.println("FIRST:");
        for(String a : first){
            System.out.println("\t"+a);
        }
//        System.out.println("FOLLOW:");
//        for(String a : follow){
//            System.out.println("\t"+a);
//        }
    }

    public void limpaProd(){
        for(int i=0; i<producoes.size(); i++){
            for(int j=0; j<producoes.get(i).size(); j++){
                if(producoes.get(i).get(j).length()==0){
                    producoes.get(i).remove(j);
                }
            }
        }
    }
}
