package fr.chokearth.irobot;

import org.luaj.vm2.LuaValue;

import java.util.ArrayList;

public class Terminal {

    private ArrayList<String> lines = new ArrayList<>();
    private ArrayList<String> input = new ArrayList<>();
    private int inputIndex = -1;
    private boolean commandLine = false;
    private String preCurrentCommande = "";
    private String currentCommande = "";

    private static final ArrayList<String> programs = new ArrayList<>();

    public Terminal(){
        lines.add("");

        programs.add("cd");
        programs.add("ls");
        programs.add("run");
    }

    public String getLine(int i){
        if(i == 0) return commandLine ? ImRobot.fileSystem.getPath()+preCurrentCommande+currentCommande : "-----------------------------";
        if(i-1 >= lines.size()) return "";

        return lines.get(i-1);
    }
    private void push(String str){
        lines.add(0, str);
        while(lines.size() > 100) lines.remove(lines.size()-1);
    }

    public void print(String str){
        String[] ts = str.split("\n");
        lines.set(0, lines.get(0) + ts[0]);
        for (int i = 1; i < ts.length; i++) {
            push("");
            lines.set(0, lines.get(0) + ts[i]);
        }
    }
    public void println(String str){
        print(str);
        push("");
    }

    public void charTyped(char c, int i) {
        currentCommande+=c;
    }

    public void keyPressed(int i1, int i2, int i3) {
        if(isCommandLine()) {
            if(i1 == 259) //return
            {
                if(currentCommande.length() > 0)
                    currentCommande = currentCommande.substring(0, currentCommande.length()-1);
            }else if(i1 == 257) { //enter
                if(currentCommande == "") return;
                input.add(currentCommande);
                println(ImRobot.fileSystem.path+preCurrentCommande + currentCommande);

                if (currentCommande.equals("reboot")) {
                    ImRobot.loadBios();
                } else if (currentCommande.split(" ")[0].equals("Lua")) {
                    System.out.println("run : " + currentCommande.substring(4));
                    ImRobot.globals.get("runLuaCommand").invoke(LuaValue.valueOf(currentCommande.substring(4)));
                } else if (!currentCommande.equals("bios")) {
                    synchronized (ImRobot.globals) {
                        System.out.println(currentCommande);
                        String[] splits = currentCommande.split(" ");
                        if(programs.contains(splits[0])){
                            String param = "";
                            for (int i = 1; i < splits.length; i++) {
                                param+="\""+splits[i] + "\"";
                                if(i != splits.length-1) param+=",";
                            }

                            ImRobot.globals.load("assert(loadfile(\""+ImRobot.fileSystem.getDirectory()+"/rom/programs/"+currentCommande.split(" ")[0]+".lua\"))("+param+")").call();
                        }
                    }
                }

                currentCommande = "";
                inputIndex = -1;
            }else if(i1 == 265){ //top
                if(inputIndex < input.size()-1){
                    inputIndex++;
                    currentCommande = input.get(input.size()-inputIndex-1);
                }
            }else if(i1 == 264){ //bottom
                if(inputIndex > -1){
                    inputIndex--;
                    currentCommande = inputIndex == -1 ? "" : input.get(input.size()-inputIndex-1);
                }
            }
        }

//        System.out.println("Pressed "+i1+"  "+i2+"  "+i3);
    }

    public void keyReleased(int i1, int i2, int i3) {
//        System.out.println("Released "+i1+"  "+i2+"  "+i3);
    }

    public boolean isCommandLine() {
        return commandLine;
    }

    public void enableCommande(String preCommand){
        this.commandLine = true;
        this.preCurrentCommande = preCommand;
    }
}
