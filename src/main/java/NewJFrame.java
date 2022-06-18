
import com.github.javaparser.ParserConfiguration;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.expr.MethodCallExpr;
import com.github.javaparser.ast.visitor.VoidVisitor;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;
import com.github.javaparser.symbolsolver.JavaSymbolSolver;
import com.github.javaparser.symbolsolver.resolution.typesolvers.CombinedTypeSolver;
import com.github.javaparser.symbolsolver.resolution.typesolvers.ReflectionTypeSolver;
import com.github.javaparser.symbolsolver.utils.SymbolSolverCollectionStrategy;
import com.github.javaparser.utils.ProjectRoot;
import com.github.javaparser.utils.SourceRoot;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import static java.lang.Integer.parseInt;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

public class NewJFrame extends javax.swing.JFrame {

    //ArrayList<String> soursepath = new ArrayList<String>();
    static ArrayList<String> soursepath = new ArrayList<String>();
    static ArrayList<String> methodmain = new ArrayList<String>();
    static ArrayList<String> arm = new ArrayList<String>();
    static ArrayList<String> methodcallgraph = new ArrayList<String>();
    
    static boolean bolmethee=true;
    static boolean bolmethee2=true;
    static double mainmethod=0;
    static boolean afalse=false;
    static boolean bfalse=false;
    static boolean whileloop=false;
    
    static ArrayList<String> namepac = new ArrayList<String>();
    static ArrayList<String> namepacparanomasths = new ArrayList<String>();
    static ArrayList<String> nameinamain = new ArrayList<String>();
    static ArrayList<String> methodcallgraphcount = new ArrayList<String>();
    static ArrayList<String> fullnamepacparanomasths = new ArrayList<String>();
    static ArrayList<String> graphcounter = new ArrayList<String>();
    static int counterarray=0;
    
    //stis koloumenes methodous mas dinei ta method call expresion
    private static class MethodVisitor extends VoidVisitorAdapter<Void>
    {@Override
        public void visit(MethodCallExpr ml, Void arg) {                
                whileloop=true;
                String mystring2=ml.getScope().toString();
                    String [] arr2 = mystring2.split("^new *");
                    int N=1; 
                    String nWords2="";
                    for(int t=0; t<N ; t++){
                        nWords2 = nWords2 + arr2[t] + "."  ;
                    }
              if(nWords2.startsWith("Optional[new")){        
                  String firstcharacter="";
                  firstcharacter=nWords2.substring(13);
                  firstcharacter = firstcharacter.replaceAll("\\(.*\\)", "");
                  firstcharacter=("Optional["+firstcharacter);
                  firstcharacter = firstcharacter.substring(0,firstcharacter.length()-1);
                  methodcallgraph.add(firstcharacter+" "+ml.getName().toString());
              }
              
              String nWordsString=nWords2;
              nWordsString = nWordsString.substring(0,nWordsString.length()-1);
              String pattern="\\.";
              Pattern p=Pattern.compile(pattern);
              Matcher m=p.matcher(nWordsString);
             if(m.find()){
                 nWordsString=nWordsString.substring(0,nWordsString.indexOf("."));
                 nWordsString=nWordsString+"]";
                 methodcallgraph.add(nWordsString+" "+ml.getName().toString());
                }
              else{
                  methodcallgraph.add(ml.traverseScope()+" "+ml.getName().toString());
              }
              methodcallgraph=removeDuplicates(methodcallgraph);       
        }
}
           //afairoume diplotupa apo array list autos o tropos mporei na alaxtei
       public static <T> ArrayList<T> removeDuplicates(ArrayList<T> list)
    {
        ArrayList<T> newList = new ArrayList<T>();
        for (T element : list) {

            if (!newList.contains(element)) {
                newList.add(element);
            }
        }
        return newList;
    } 
          
    private static class MethodVisitor2 extends VoidVisitorAdapter<Void>{
        @Override
        public void visit(MethodCallExpr n, Void arg) {
            
        super.visit(n, arg);
        try{
            String mystring2=n.getScope().toString();
                    String [] arr2 = mystring2.split("^new *");
                    int N=1; 
                    String nWords2="";
                    for(int t=0; t<N ; t++){
                        nWords2 = nWords2 + arr2[t] + "."  ;
                    }
              if(nWords2.startsWith("Optional[new")){
                  String firstcharacter="";
                  firstcharacter=nWords2.substring(13);
                  firstcharacter = firstcharacter.replaceAll("\\(.*\\)", "");
                  firstcharacter=("Optional["+firstcharacter);
                  firstcharacter = firstcharacter.substring(0,firstcharacter.length()-1);
                  arm.add(firstcharacter+" "+n.getName().toString());
              }
              String nWordsString=nWords2;
              nWordsString = nWordsString.substring(0,nWordsString.length()-1);
              String pattern="\\.";
            Pattern p=Pattern.compile(pattern);
            Matcher m=p.matcher(nWordsString);
            if(m.find()){
                 nWordsString=nWordsString.substring(0,nWordsString.indexOf("."));
                 nWordsString=nWordsString+"]";
                 arm.add(nWordsString+" "+n.getName().toString());
                }
              else{
                  arm.add(n.traverseScope()+" "+n.getName().toString());
              }         
        }
        catch (Exception e) {
        e.printStackTrace();
        return;} 
        }
    }
    public static String[] myBadGlobalArray = new String[10];
        //statik metablites stous metrites gia methodous kai bibliothikes
         public  static int i=0,i2=0;
         
        private static class MethodNamePrinter2 extends VoidVisitorAdapter<Void> {
        @Override
        public void visit(MethodDeclaration mc, Void arg) {
        super.visit(mc, arg);
            String if1,if2;

            for( int i=0; i<methodcallgraph.size(); i++){
                if1=methodcallgraph.get(i).toLowerCase();
                if2=("Optional["+mc.resolve().getClassName().toLowerCase()+"] "+mc.resolve().getName()).toLowerCase();
 
                if(if1.equals(if2)){
                    
                    String mystring2=mc.resolve().getPackageName();
                    String [] arr2 = mystring2.split("\\.");
                    int N=3; 
                    String nWords2="";
                    for(int n=0; n<N ; n++){
                        nWords2 = nWords2 + arr2[n] + "."  ;
                    }
 
                    boolean newflag=true;

                    for(int counteri=0; counteri<methodcallgraphcount.size(); counteri++){
                        if( methodcallgraphcount.get(counteri).equals(mc.resolve().getName())){
                            newflag=false;
                        }
                            
                    }
                   
                    if(newflag)
                        {
                            for(int counti=0; counti<namepac.size(); counti++){
                                if(namepac.get(counti).equals(nWords2)){
                                    String graphcounterString=graphcounter.get(counti);
                                    System.out.println("Method in Name Printed: "+mc.resolve().getQualifiedName());
                                    int graphcounterInt=parseInt(graphcounterString);
                                    graphcounterInt++;
                                    graphcounter.set(counti,String.valueOf(graphcounterInt));
                                    }
                                }
                        }
                    methodcallgraphcount.add(mc.resolve().getName());
                    methodcallgraphcount=removeDuplicates(methodcallgraphcount);
                    VoidVisitor<Void> methodNameVisitor4 = new MethodVisitor();
                    methodNameVisitor4.visit(mc, null);
                }     
            }
            counterarray=methodcallgraph.size()-1;
            } 
        }
        //metrame to plithos toon methodon kai toon biblhothikon
        private static class MethodNamePrinter extends VoidVisitorAdapter<Void> {
        @Override
        public void visit(MethodDeclaration md, Void arg) {
        super.visit(md, arg);
        i2++;
        
        String mystring=md.resolve().getPackageName();
            String [] arr = mystring.split("\\.");
            int N=3; 
            String nWords="";
            for(int i=0; i<N ; i++){
                nWords = nWords + arr[i] + "."  ;
            }
            if(bolmethee){
                namepac.add(nWords);
                namepacparanomasths.add("0");
                nameinamain.add("0");
                fullnamepacparanomasths.add("0");
                graphcounter.add("0");
                bolmethee=false;
                }
            int countermain=0;
            int kametr=0,counter;
            for(counter=0; counter<namepac.size(); counter++){
                if(namepac.get(counter).equals(nWords)){
                    String na=fullnamepacparanomasths.get(counter);
                    countermain=counter;
                    int nana=parseInt(na);
                    nana++;
                    fullnamepacparanomasths.set(counter,String.valueOf(nana));
                    }
                    if(!namepac.get(counter).equals(nWords)){
                        kametr++;                    
                }
          }
                 if(counter==kametr){  
                    namepac.add(nWords);
                    namepacparanomasths.add("0");
                    nameinamain.add("0");
                    fullnamepacparanomasths.add("0");
                    graphcounter.add("0");                    
                }
        if(md.isPublic()){
            i++;

            for(counter=0; counter<namepac.size(); counter++){
                if(namepac.get(counter).equals(nWords)){
                    String namepacparanomasthscounter=namepacparanomasths.get(counter);
                    countermain=counter;
                    int namepacparanomasthscounterParse=parseInt(namepacparanomasthscounter);
                    namepacparanomasthscounterParse++;
                    namepacparanomasths.set(counter,String.valueOf(namepacparanomasthscounterParse));
                        }  
                    }
                        for(int i=0; i<methodmain.size(); i++){
                            String if1,if2;
                            if1=methodmain.get(i).toLowerCase();
                            if2="Optional["+md.resolve().getClassName().toLowerCase()+"] "+md.resolve().getName().toLowerCase();
                            if(if1.toLowerCase().equals(if2.toLowerCase())){
                                System.out.println("Method Name Printed: "+md.resolve().getQualifiedName());
                                String nameinamainString=nameinamain.get(countermain);
                                int nameinamainInt=parseInt(nameinamainString);
                                nameinamainInt++;                              
                                VoidVisitor<Void> methodNameVisitor4 = new MethodVisitor();
                                methodNameVisitor4.visit(md, null);
                                if(whileloop){
                                    CombinedTypeSolver combinedTypeSolver = new CombinedTypeSolver();
                                    combinedTypeSolver.add(new ReflectionTypeSolver());
                                    ParserConfiguration parserConfiguration = new ParserConfiguration().setSymbolResolver(new JavaSymbolSolver(combinedTypeSolver));
                                    ProjectRoot projectRoot = new SymbolSolverCollectionStrategy(parserConfiguration).collect(Path.of(".\\target\\lib\\sources"));
                                    for (SourceRoot sourceRoot : projectRoot.getSourceRoots()) {
                                        try {
                                        sourceRoot.tryToParse();
                                        List<CompilationUnit> compilationUnits = sourceRoot.getCompilationUnits();
                                        for(int x=0; x<compilationUnits.size();x++){

                                            VoidVisitor<Void> methodNameVisitor = new MethodNamePrinter2();
                                                methodNameVisitor.visit(compilationUnits.get(x), null);

                                        }
                                        } catch (IOException e) {
                                            e.printStackTrace();
                                            return;
                                        }
                                    }
                                }  
                            nameinamain.set(countermain,String.valueOf(nameinamainInt));     
                            mainmethod=mainmethod+1;
                            System.out.println(mainmethod);
                            }
                    }
                    
                }
        }          
 }
        //emafanizoume to plhthos ton methodon 
        public static int count(){
            System.out.println("Number of methods: "+i);
            return i;
            
        }
    //einai methodos gia na trexoume cmd entoles
    public static void CMD(String cmd)
    {
        String[] command =
	    {
	        "cmd",
	    };
	    Process p;
		try {
			p = Runtime.getRuntime().exec(command);
		        new Thread(new SyncPipe(p.getErrorStream(), System.err)).start();
	                new Thread(new SyncPipe(p.getInputStream(), System.out)).start();
	                PrintWriter stdin = new PrintWriter(p.getOutputStream());
                        stdin.println(cmd);
	                stdin.close();
	                p.waitFor();
	    	} catch (Exception e) {
	 		e.printStackTrace();
	}
    }
    //pairnoyme ta path pou theloume
    private static final String FILE_PATH4=".\\target\\lib\\sources";

    public NewJFrame() {
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        RunButton = new javax.swing.JButton();
        InputTextField = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        ExportjTextArea = new javax.swing.JTextArea();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        RunButton.setText("Run");
        RunButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                RunButtonActionPerformed(evt);
            }
        });

        ExportjTextArea.setColumns(20);
        ExportjTextArea.setRows(5);
        jScrollPane1.setViewportView(ExportjTextArea);

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel1.setText("Enter Path");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(63, 63, 63)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 692, Short.MAX_VALUE)
                            .addComponent(InputTextField)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(284, 284, 284)
                                .addComponent(RunButton, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(337, 337, 337)
                        .addComponent(jLabel1)))
                .addContainerGap(45, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(31, 31, 31)
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addComponent(InputTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(31, 31, 31)
                .addComponent(RunButton, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 306, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void RunButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_RunButtonActionPerformed
        // TODO add your handling code here:
        ExportjTextArea.append("Wait");
        String FILE_PATH3;
        FILE_PATH3 = InputTextField.getText();
        
        //Symbolsover gia th main tou project
        CombinedTypeSolver combinedTypeSolver2 = new CombinedTypeSolver();
        combinedTypeSolver2.add(new ReflectionTypeSolver());
        ParserConfiguration parserConfiguration2 = new ParserConfiguration().setSymbolResolver(new JavaSymbolSolver(combinedTypeSolver2));
        ProjectRoot projectRoot2 = new SymbolSolverCollectionStrategy(parserConfiguration2).collect(Path.of(FILE_PATH3+"\\src\\main"));
        for (SourceRoot sourceRoot : projectRoot2.getSourceRoots()) {
    try {
        sourceRoot.tryToParse();
        List<CompilationUnit> compilationUnits = sourceRoot.getCompilationUnits();
        for(int x=0; x<compilationUnits.size();x++){
            VoidVisitor<Void> methodNameVisitor2 = new MethodVisitor2();
                methodNameVisitor2.visit(compilationUnits.get(x), null);
        }
    } catch (IOException e) {
        e.printStackTrace();
        return;}}
        methodmain=removeDuplicates(arm);
        //maven entoles gia extract gia jar kai sourses
        String CWD = System.getProperty("user.dir");       
        String cmd_Command="cd " + FILE_PATH3 +" & mvn dependency:copy-dependencies -DexcludeTransitive -DoutputDirectory="+CWD+"\\target\\lib";     
        CMD(cmd_Command);   
        String cmd2 ="dir /a:-d /s /b "+CWD + "\\target\\lib"+" | find /c \":\" ";       
        CMD(cmd2);       
        String cd_Command="cd " + FILE_PATH3+"& mvn dependency:copy-dependencies -Dclassifier=sources  -DexcludeTransitive -DoutputDirectory="+CWD+"\\target\\lib\\sources";       
        CMD(cd_Command);       
        File folder = new File(".\\target\\lib\\sources");      
        File[] listOfFiles = folder.listFiles();
        for (int i = 0; i < listOfFiles.length; i++) {
            String cmd3="cd "+FILE_PATH4 +" & jar xf "+listOfFiles[i].getName();;
            CMD(cmd3);      
        }
        //Symbolsover gia ta jar arxeia
        CombinedTypeSolver combinedTypeSolver = new CombinedTypeSolver();
        //combinedTypeSolver.add(new JavaParserTypeSolver(new File("C:\\Users\\mario\\Desktop\\mavenperser\\target\\lib\\sources")));
        combinedTypeSolver.add(new ReflectionTypeSolver());
        ParserConfiguration parserConfiguration = new ParserConfiguration().setSymbolResolver(new JavaSymbolSolver(combinedTypeSolver));
        ProjectRoot projectRoot = new SymbolSolverCollectionStrategy(parserConfiguration).collect(Path.of(".\\target\\lib\\sources"));
        for (SourceRoot sourceRoot : projectRoot.getSourceRoots()) {
    try {
        sourceRoot.tryToParse();
        List<CompilationUnit> compilationUnits = sourceRoot.getCompilationUnits();
        //System.out.println(compilationUnits);
        for(int x=0; x<compilationUnits.size();x++){
            
            VoidVisitor<Void> methodNameVisitor = new MethodNamePrinter();
                methodNameVisitor.visit(compilationUnits.get(x), null);
                
        }
    } catch (IOException e) {
        e.printStackTrace();
        return;
    }

        System.out.println("oi  methodoi pou brethikan "+mainmethod+" SYNOLIKES METHODOI ");
        ExportjTextArea.setText("Oi methodoi poy xrhsimopoiounte ston kodika einai "+mainmethod+"\n");
        int a=count();
        ExportjTextArea.append("oi synolikes methodoi twn jar arxeion "+a+"\n");
        double b=(mainmethod/a)*100;
        System.out.println("Apotelesma "+b+" %");
        ExportjTextArea.append("Apotelesma "+b+"%"+"\n");
        System.out.println("------------------------------------------------");
        System.out.println(namepac);
        
        System.out.println(namepacparanomasths);
        System.out.println(nameinamain);
        double sum;
        
        for(int i=0; i<nameinamain.size(); i++){
            sum=(Double.parseDouble(nameinamain.get(i))/Double.parseDouble(namepacparanomasths.get(i)))*100;
            System.out.println(namepac.get(i)+"  "+sum+" %");
            ExportjTextArea.append(namepac.get(i)+"  "+sum+" %"+"\n");
        }
        
        System.out.println("------------------------------------------------");
        ExportjTextArea.append("-------------------------------------"+"\n");
        ExportjTextArea.append("oi diplotipes methodoi einai "+methodcallgraphcount.size()+"\n");
        System.out.println("oi diplotipes methodoi einai "+methodcallgraphcount.size()+"\n");
        System.out.println(fullnamepacparanomasths);
        System.out.println(graphcounter);
        for(int i=0; i<fullnamepacparanomasths.size(); i++){
           if(!graphcounter.get(i).equals("0"))
                {
                String graphcounterString=graphcounter.get(i);             
                    int graphcounterInt=parseInt(graphcounterString);
                    graphcounterInt++;
                    graphcounter.set(i,String.valueOf(graphcounterInt));
                    }

            sum=(Double.parseDouble(graphcounter.get(i))/Double.parseDouble(fullnamepacparanomasths.get(i)))*100;
            System.out.println(namepac.get(i)+"  "+sum+" %");
            ExportjTextArea.append(namepac.get(i)+"  "+sum+" %"+"\n");
            System.out.println();
        }
      }
    }//GEN-LAST:event_RunButtonActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) throws FileNotFoundException, IOException, SAXException, ParserConfigurationException {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(NewJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(NewJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(NewJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(NewJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new NewJFrame().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextArea ExportjTextArea;
    private javax.swing.JTextField InputTextField;
    private javax.swing.JButton RunButton;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    // End of variables declaration//GEN-END:variables
}
