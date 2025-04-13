package mvc;

import java.io.Serializable;

public abstract class Model extends Publisher implements Serializable {
    public boolean unsavedChanges = false;
    public String fileName = null;

    public void changed() {
        unsavedChanges = true;
        notifySubscribers();
    }

    public void setUnsavedChanges(boolean b) {
        unsavedChanges = b;
    }

    public void setFileName(String fName) {
        fileName = fName;
    }

    public String getFileName() {
        return fileName;
    }

    public boolean getUnsavedChanges() {
        return unsavedChanges;
    }
}