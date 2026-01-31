package es.xcm.hunger.config;

public sealed interface HudPosition
        permits HudPosition.Preset, HudPosition.Custom {

    LayoutMode layoutMode();
    int left();
    int bottom();
    String name();

    enum Preset implements HudPosition {
        BottomLeft(LayoutMode.Bottom, 12, 12),
        AboveHotbarCentered(LayoutMode.Center, -12, 140),
        AboveHotbarLeft(LayoutMode.Center, -363, 140),
        BelowHotbarCentered(LayoutMode.Center, -12, 12),
        BelowHotbarLeft(LayoutMode.Center, -363, 8);

        private final LayoutMode layoutMode;
        private final int left;
        private final int bottom;

        Preset(LayoutMode layoutMode, int left, int bottom) {
            this.layoutMode = layoutMode;
            this.left = left;
            this.bottom = bottom;
        }

        @Override public LayoutMode layoutMode() { return layoutMode; }
        @Override public int left()   { return left; }
        @Override public int bottom() { return bottom; }
    }

    record Custom(LayoutMode layoutMode, int left, int bottom) implements HudPosition {
        @Override
        public String name() {
            return "Custom:" +  layoutMode + ":" + left + ":" + bottom;
        }
    }

    static HudPosition pluginDefault() {
        return Preset.AboveHotbarCentered;
    }

    static HudPosition valueOf (String name) {
        if (name == null || name.isEmpty()) return null;

        String[] parts = name.split(":");
        if (parts.length == 1) {
            try {
                return Preset.valueOf(name);
            } catch (IllegalArgumentException e) {
                return null;
            }
        // preserve old custom positions working
        } else if (parts.length == 3 && parts[0].equals("Custom")) {
            int left = Integer.parseInt(parts[1]);
            int bottom = Integer.parseInt(parts[2]);
            return new Custom(LayoutMode.Bottom, left, bottom);
        } else if (parts.length == 4 && parts[0].equals("Custom")) {
            LayoutMode layoutMode = LayoutMode.valueOf(parts[1]);
            int left = Integer.parseInt(parts[2]);
            int bottom = Integer.parseInt(parts[3]);
            return new Custom(layoutMode, left, bottom);
        }
        else {
            return null;
        }
    }
}