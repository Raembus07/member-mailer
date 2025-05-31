package ch.josiaschweizer.entity.user.email;

import javax.annotation.Nonnull;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Email {

    private Map<EmailComposition, String> emailMap = new HashMap<>();

    public Email() {
        // Default constructor
    }

    public Email(EmailComposition emailComposition, String email) {
        emailMap.put(emailComposition, email);
    }

    @Nonnull
    public Email addEmail(@Nonnull final EmailComposition emailComposition,
                          final String email) {
        if (email == null || email.isEmpty()) {
            System.err.println("Email is null or empty, not adding to user.");
            return this;
        }
        if (emailMap.containsKey(emailComposition)) {
            System.err.println("Email for composition " + emailComposition + " already exists, not adding to user.");
            return this;
        }
        emailMap.put(emailComposition, email);
        return this;
    }

    public String getSenderEmail() {
        if (emailMap.containsKey(EmailComposition.PRIMARY)) {
            return emailMap.get(EmailComposition.PRIMARY);
        } else if (emailMap.containsKey(EmailComposition.SECONDARY)) {
            return emailMap.get(EmailComposition.SECONDARY);
        }
        return null;
    }

    public String getEmail(@Nonnull final EmailComposition emailComposition) {
        return emailMap.get(emailComposition);
    }
}
