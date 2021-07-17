package com.semihbkgr.corbeau.error;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

public class IncorrectCredentialException extends RuntimeException{

    private List<Credential> incorrectCredentialList;

    public IncorrectCredentialException() {
    }

    public IncorrectCredentialException(@NonNull List<Credential> incorrectCredentialList) {
        this.incorrectCredentialList = incorrectCredentialList;
    }

    public void addIncorrectCredential(@NonNull Credential credential){
        if(incorrectCredentialList==null)
           this.incorrectCredentialList=new ArrayList<>();
        incorrectCredentialList.add(credential);
    }

    public void addIncorrectCredential(@NonNull String name,@NonNull String value){
        addIncorrectCredential(Credential.builder().name(name).value(value).build());
    }

    public boolean hasIncorrectCredential(){
        return this.incorrectCredentialList!=null;
    }

    public void throwIfHasIncorrectCredential() throws IncorrectCredentialException{
        if(hasIncorrectCredential())
            throw this;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Credential{
        private String name;
        private String value;
    }

}
