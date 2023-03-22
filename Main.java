/*---------------------------*/
/*Name: Khaled Elgohary*/
/*Student Number: 7924188*/
/*Course: Comp4190*/
/*Instructor: Cuneyt ackora */
/*Assignment:2*/

public class Main {
    public static void main(String[] args){
        System.out.println("-------------EXAMPLE 1(TESTING EXAMPLE)----------------");
        Factor b=new Factor(new String[]{"B"},new String[][]{{"+b"},{"-b"}},new double[]{0.3,0.7});
        Factor e=new Factor(new String[]{"E"},new String[][]{{"+e"},{"-e"}},new double[]{0.1,0.9});
        Factor m3=new Factor(new String[]{"A","B","E"},new String[][]{{"+a","+b","+e"},{"+a","+b","-e"},
                {"+a","-b","+e"},{"+a","-b","-e"},{"-a","+b","+e"},{"-a","+b","-e"},{"-a","-b","+e"},
                {"-a","-b","-e"}},new double[]{0.8,0.7,0.2,0.1,0.2,0.3,0.8,0.9});
        Factor m4=new Factor(new String[]{"W","A"},new String[][]{{"+w","+a"},{"+w","-a"},{"-w","+a"},{"-w","-a"}},
                new double[]{0.8,0.4,0.2,0.6});
        Factor[] facts={b,e,m3,m4};
        String[] queryVariables={"B"};
        String[] evidenceVars={"-a"};
        String[] ordredList={"W","E"};
        Factor f=Factor.inference(facts,queryVariables,ordredList,evidenceVars);
        System.out.println("-------------EXAMPLE 1 TESTING ENDING----------");
        //the above example shows the corret output for the inference asked for, and was done for the purpose of testing
        System.out.println("------------A1 Part 2 Question 1 , prior probability----------------");
        Factor fraud=new Factor(new String[]{"T","F"},new String[][]{{"+t","-f"},{"+t","+f"},{"-t","-f"},{"t","+f"}},
                new double[]{0.01,0.9,0.004,0.001});
        Factor trav=new Factor(new String[]{"T"},new String[][]{{"+t"},{"-t"}},new double[]{0.05,0.95});
        Factor q=new Factor(new String[]{"T","F","P"},new String[][]{{"+t","+f","+p"},{"+t","+f","-p"},
                {"+t","-f","+p"},{"+t","-f","-p"},{"-t","+f","+p"},{"-t","+f","-p"},{"-t","-f","+p"},
                {"-t","-f","-p"}},new double[]{0.9,0.1,0.1,0.9,0.01,0.99,0.004,0.996});
        Factor r=new Factor(new String[]{"I","C","F"},new String[][]{{"+i","-c","+f"},{"+i","+c","+f"},
                {"+i","-c","-f"},{"+i","+c","-f"},{"-i","-c","+f"},{"-i","+c","+f"},{"-i","-c","-f"},{"-i","+c","-f"}},
                new double[]{0.0011,0.001,0.002,0.01,0.0001,0.001,0.9989,0.988});
        Factor s=new Factor(new String[]{"R","C"},new String[][]{{"+r","-c"},{"+r","+c"},{"-r","-c"},{"-r","+c"}},
                new double[]{0.001,0.1,0.999,0.9});
        Factor m=Factor.inference(new Factor[]{fraud,trav,q,r,s},new String[]{"F"},new String[]{"C","T","P","R"},new String[]{});
        System.out.println("-----------------A2 PART2 Q1 END---------");

        System.out.println("-----------------A2 PART2 Q2 START-------------");
        Factor p=new Factor(new String[]{"F"},new String[][]{{"+f"},{"-f"}},new double[]{0.005,0.995});
        Factor t=new Factor(new String[]{"T"},new String[][]{{"+t"},{"-t"}},new double[]{0.05,0.95});
        Factor fp=new Factor(new String[]{"T","F","P"},new String[][]{{"+t","+f","+p"},{"+t","+f","-p"},
                {"+t","-f","+p"},{"+t","-f","-p"},{"-t","+f","+p"},{"-t","+f","-p"},{"-t","-f","+p"},
                {"-t","-f","-p"}},new double[]{0.9,0.1,0.9,0.1,0.01,0.99,0.01,0.99});
        Factor ip=new Factor(new String[]{"C","F","I"},new String[][]{{"+c","+f","+i"},{"+c","+f","-i"},
                {"+c","-f","+i"},{"+c","-f","-i"},{"-c","+f","+i"},{"-c","+f","-i"},{"-c","-f","+i"},
                {"-c","-f","-i"}},new double[]{0.02,0.98,0.01,0.99,0.11,0.89,0.001,0.999});
        Factor crp=new Factor(new String[]{"C","R"},new String[][]{{"+c","+r"},{"+c","-r"},{"-c","+r"},
                {"-c","-r"}},new double[]{0.1,0.9,0.001,0.999});
        Factor infer=Factor.inference(new Factor[]{p,t,fp,ip,crp},new String[]{"F"},new String[]{"C","T"},
                new String[]{"+p","-i","+r"});

    }
}
