package binnie.core.craftgui.events;

import binnie.core.craftgui.IWidget;

public abstract class EventHandler<E extends Event> {

    Class<E> eventClass;
    Origin origin;
    IWidget relative;

    public EventHandler(Class<E> eventClass) {
        this.eventClass = eventClass;
        origin = Origin.Any;
    }

    public EventHandler setOrigin(Origin origin, IWidget relative) {
        this.origin = origin;
        this.relative = relative;
        return this;
    }

    public abstract void onEvent(E event);

    public boolean handles(Event e) {
        return eventClass.isInstance(e) && origin.isOrigin(e.getOrigin(), relative);
    }

    public enum Origin {

        Any,
        Self,
        Parent,
        DirectChild;

        public boolean isOrigin(IWidget origin, IWidget test) {
            return switch (this) {
                case Any -> true;
                case DirectChild -> test.getWidgets().contains(origin);
                case Parent -> test.getParent() == origin;
                case Self -> test == origin;
            };
        }
    }
}
