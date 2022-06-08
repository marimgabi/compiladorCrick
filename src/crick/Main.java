package crick;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main {

    public static boolean containString(List<String> lista, String a){
        for(String aux : lista){
            if(aux.equals(a)){
                return true;
            }
        }
        return false;
    }

    public static void main(String[] args) throws IOException {

        Character[] numbers = new Character[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9'};
        List<Character> numbersList = Arrays.asList(numbers);

        Character[] chars = new Character[]{'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm',
                'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z',
                'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M',
                'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'};
        List<Character> charsList = Arrays.asList(chars);

        String[] keywords = new String[]{"int", "double", "char", "bool", "true", "false", "if", "else", "switch",
                "case", "break", "default", "for", "while", "dataBlock", "mainBlock", "scan", "print"};
        List<String> keywordsList = Arrays.asList(keywords);



        List<Lexema> lexemes = new ArrayList<>();

        int contLinha;

        //Leitura do arquivo
        String path = "src/crick/fonte.kls";
//        String path = "src/crick/teste.kls";
        contLinha=0;
        BufferedReader buffRead = new BufferedReader(new FileReader(path));
        String linha = "";
        while (true) {
            if (linha != null) {
                if(linha.length()>0){
                    int estado=1, pointer=0;
                    String lexeme = "";
                    while (pointer<linha.length()){
                        char c = linha.charAt(pointer);
                        switch(estado){
                            //ESTADO 1
                            case 1:
                                if(charsList.contains(c)) {
                                    lexeme = lexeme+c;
                                    estado = 8;
                                }else if(numbersList.contains(c)) {
                                    lexeme = lexeme + c;
                                    estado = 3;
                                }else if((c=='+')||(c=='*')||(c=='/')) {
                                    lexeme = lexeme + c;
                                    Lexema l = new Lexema("AritOp",lexeme,contLinha);
                                    lexemes.add(l);
                                    lexeme = "";
                                    estado = 1;
                                }else if(c=='-') {
                                    lexeme = lexeme + c;
                                    estado = 2;
                                }else if((c=='&')||(c=='|')||(c=='^')||(c=='~')) {
                                    lexeme = lexeme + c;
                                    Lexema l = new Lexema("LogOp",lexeme,contLinha);
                                    lexemes.add(l);
                                    lexeme = "";
                                    estado = 1;
                                }else if((c=='>')||(c=='<')) {
                                    lexeme = lexeme + c;
                                    estado = 19;
                                }else if(c=='!') {
                                    lexeme = lexeme + c;
                                    estado = 21;
                                }else if(c=='=') {
                                    lexeme = lexeme + c;
                                    estado = 23;
                                }else if((c=='(')||(c==')')||(c=='{')||(c=='}')||(c==';')||(c==',')||(c=='"')||(c==':')){
                                    lexeme = lexeme + c;
                                    Lexema l = new Lexema("symbol",lexeme,contLinha);
                                    lexemes.add(l);
                                    lexeme = "";
                                    estado = 1;
                                }else if((c=='\n')||(c=='\r')||(c=='\t')||(c==' ')){
                                    estado=1;
                                }else{
                                    //ERRO
                                    System.out.println("Erro estado 1 - "+lexeme);
                                }
                                break;

                            //ESTADO 2
                            case 2:
                                if(numbersList.contains(c)){
                                    lexeme = lexeme+c;
                                    estado = 3;
                                }else{
                                    Lexema l = new Lexema("AritOp",lexeme,contLinha);
                                    lexemes.add(l);
                                    lexeme="";
                                    pointer--;
                                    estado=1;
                                }
                                break;

                                //ESTADO 3
                                case 3:
                                if(numbersList.contains(c)) {
                                    lexeme = lexeme + c;
                                    estado = 3;
                                }else if(c=='.'){
                                    lexeme = lexeme + c;
                                    estado = 5;
                                }else{
                                    Lexema l = new Lexema("int",lexeme,contLinha);
                                    lexemes.add(l);
                                    lexeme="";
                                    pointer--;
                                    estado=1;
                                }
                                break;

                            //ESTADO 5
                            case 5:
                                if(numbersList.contains(c)) {
                                    lexeme = lexeme + c;
                                    estado = 6;
                                }else{
                                    //ERRO
                                    System.out.println("Erro estado 5 - "+lexeme);
                                    lexeme="";
                                    pointer--;
                                    estado=1;
                                }
                                break;

                            //ESTADO 6
                            case 6:
                                if(numbersList.contains(c)) {
                                    lexeme = lexeme + c;
                                    estado = 6;
                                }else{
                                    lexeme = lexeme+c;
                                    Lexema l = new Lexema("double",lexeme,contLinha);
                                    lexemes.add(l);
                                    lexeme="";
                                    pointer--;
                                    estado=1;
                                }
                                break;

                            //ESTADO 8
                            case 8:
                                if(charsList.contains(c) || numbersList.contains(c)){
                                    lexeme = lexeme+c;
                                    estado = 8;
                                }else{
                                    if(keywordsList.contains(lexeme)){
                                        Lexema l = new Lexema("keyword",lexeme,contLinha);
                                        lexemes.add(l);
                                    }else{
                                        //Variável
                                        Lexema l = new Lexema("var",lexeme,contLinha);
                                        lexemes.add(l);
                                    }
                                    lexeme="";
                                    pointer--;
                                    estado=1;
                                }
                                break;

                            //ESTADO 19
                            case 19:
                                if(c=='='){
                                    lexeme = lexeme+c;
                                    estado = 20;
                                }else{
                                    lexeme=lexeme+c;
                                    Lexema l = new Lexema("RelOp",lexeme,contLinha);
                                    lexemes.add(l);
                                    lexeme="";
                                    pointer--;
                                    estado=1;
                                }
                                break;

                            //ESTADO 20
                            case 20:
                                lexeme=lexeme+c;
                                Lexema l = new Lexema("RelOp",lexeme,contLinha);
                                lexemes.add(l);
                                lexeme="";
                                pointer--;
                                estado=1;
                                break;

                            //ESTADO 21
                            case 21:
                                if(c=='='){
                                    lexeme = lexeme+c;
                                    estado = 20;
                                }else{
                                    System.out.println("Erro estado 21 - "+lexeme);
                                    lexeme="";
                                    pointer--;
                                    estado=1;
                                }
                                break;

                            //ESTADO 23
                            case 23:
                                if(c=='='){
                                    lexeme = lexeme+c;
                                    estado = 20;
                                }else{
                                    //assign
                                    lexeme=lexeme+c;
                                    Lexema n = new Lexema("Assign",lexeme,contLinha);
                                    lexemes.add(n);
                                    lexeme="";
                                    pointer--;
                                    estado=1;
                                }
                                break;
                        }
                        pointer++;
                    }

                }
            } else
                break;
            linha = buffRead.readLine();
            contLinha++;
        }
//        for(Lexema l : lexemes){
//            System.out.println(l);
//        }
        buffRead.close();

        //ANÁLISE SINTÁTICA-------------------------------------------------------

        //Leitura da gramática

        List<Estado> estados = new ArrayList<>();

        BufferedReader buffRead1 = new BufferedReader(new FileReader("src/crick/gramatica.txt"));
//        BufferedReader buffRead1 = new BufferedReader(new FileReader("src/crick/teste.txt"));
        linha = "";

        while (true) {
            if (linha != null) {
//                System.out.println(linha);
                String[] output = linha.split("->");
                String[] outputEsq = new String[]{};
                String[] outputProd= new String[]{};
                String esquerdo="";
                List<String> prodEsq = new ArrayList<>();
                for(int i=0; i<output.length; i++){
                    if(i==0){
                        outputEsq = output[0].split(" ");
                        esquerdo=outputEsq[0];
                    }else if(i==1){
                        outputProd = output[1].split(" ");
                        for(int j=0; j<outputProd.length; j++){
                            String aux = outputProd[j];
                            prodEsq.add(aux);
                        }
                    }
                }

                boolean achou=false;
                for(Estado aux : estados){
                    if(aux.NTerminal.equals(esquerdo)){
                        aux.producoes.add(prodEsq);
                        achou=true;
                    }
                }

                if(achou==false){
                    Estado a = new Estado();
                    a.NTerminal=esquerdo;
                    a.producoes.add(prodEsq);
                    estados.add(a);
                }

            }else
                break;
            linha = buffRead1.readLine();
        }
        buffRead1.close();

        estados.remove(0);

        //Calculo dos first-----------------

        //LIsta com os não terminais da gramática
        List<String> nTerminais = new ArrayList<>();
        for(Estado a: estados){
            nTerminais.add(a.NTerminal);
        }

        //Limpa espaços criados na leitura da gramática
        for(Estado a : estados){
            a.limpaProd();
        }



        for(Estado atual : estados){
            System.out.println(atual.NTerminal+"----------------");
            for(List<String> p : atual.producoes){
//                int cont = 0;
//                //Se é terminal
//                if(!nTerminais.contains(p.get(cont))){
//                    System.out.println("entrou aqui");
//
//                    atual.first.add(p.get(cont));
//                }else{
//
//                }
            }
        }

//        for(Estado a: estados){
//            a.printFF();
//        }

    }
}
