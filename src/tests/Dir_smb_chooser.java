package tests;

import java.io.File;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.stage.Window;
import jcifs.smb.SmbFile;


public final class Dir_smb_chooser {
    /**
     * The title of the displayed dialog.
     */
    private StringProperty title;

    public final void setTitle(final String value) {
        titleProperty().set(value);
    }

    public final String getTitle() {
        return (title != null) ? title.get() : null;
    }

    public final StringProperty titleProperty() {
        if (title == null) {
            title = new SimpleStringProperty(this, "title");
        }

        return title;
    }

    /**
     * The initial directory for the displayed dialog.
     */
    private ObjectProperty<SmbFile> initialDirectory;

    public final void setInitialDirectory(final SmbFile value) {
        initialDirectoryProperty().set(value);
    }

    public final SmbFile getInitialDirectory() {

        return (initialDirectory != null) ? initialDirectory.get() : null;
    }

    public final ObjectProperty<SmbFile> initialDirectoryProperty() {
        if (initialDirectory == null) {
            initialDirectory =
                    new SimpleObjectProperty<SmbFile>(this, "initialDirectory");
        }

        return initialDirectory;
    }


    //---- my method ----


}
