package Ex5;

enum Role {
    ADMIN, MODERATOR, USER
}

enum Action {
    DELETE_USER, LOCK_USER, VIEW_PROFILE
}

class User {
    private Role role;

    public User(Role role) {
        this.role = role;
    }

    public Role getRole() {
        return role;
    }
}

class AccessControlService {

    public boolean canPerformAction(User user, Action action) {
        if (user == null || user.getRole() == null || action == null) {
            return false;
        }

        switch (user.getRole()) {
            case ADMIN:
                return true;
            case MODERATOR:
                return action == Action.LOCK_USER || action == Action.VIEW_PROFILE;
            case USER:
                return action == Action.VIEW_PROFILE;
            default:
                return false;
        }
    }
}

