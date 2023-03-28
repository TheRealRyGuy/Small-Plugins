package me.ryguy.reports.util;

import lombok.NoArgsConstructor;
import me.ryguy.reports.ReportsPlugin;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

@NoArgsConstructor
public class ChatReport {
    private static final String name = "[" + new SimpleDateFormat("MM.dd.yyyy hh:mm:ss").format(new Date()) + "] %s reported by %s.txt";
    private static final String[] header = new String[]{
            "[-- Chat Report --] \n",
            "Defendant: %s\n",
            "Reported by: %s\n",
            "Reason: %s\n",
            "Reported on: " + new SimpleDateFormat("MM / dd / yyyy").format(new Date()) + new SimpleDateFormat("hh:mm:ss").format(new Date()) + "\n \n"
    };

    private File file;
    private Date timestamp;
    private UUID plaintiff;
    private UUID defendant;
    private String reason;

    public ChatReport(Player defendant, Player plaintiff, String reason) {
        this.defendant = defendant.getUniqueId();
        this.plaintiff = plaintiff.getUniqueId();
        this.reason = reason;
        this.timestamp = new Date();
    }

    public void makeFile() throws IOException {
        if(file != null)
            throw new UnsupportedOperationException("There is already a file bound to this ChatReport!");
        File file = new File(ReportsPlugin.getInstance().getChatReportsDirectory(), String.format(name, defendant, plaintiff));
        if(file.exists())
            throw new IOException("Chatreport File " + file.getName() + " already exists? Possible duplicate");
        file.createNewFile();
    }

}
