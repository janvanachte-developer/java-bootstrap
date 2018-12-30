package io.vanachte.jan.bootstrap.jpa;

// https://vladmihalcea.com/how-to-audit-entity-modifications-using-the-jpa-entitylisteners-embedded-and-embeddable-annotations/
public interface Auditable {

    Audit getAudit();

    void setAudit(Audit audit);
}
