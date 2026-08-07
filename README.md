**A collection of small Fabric mods for the Rainbow Kitty Club server.**

# Recipe Tweaks

### Name Tag Recipe
- Part of the `rktweaks:craftable_name_tag` datapack. Enabled by default.
![Name Tag Recipe](img/name_tag_recipe.png)

### Cheaper Rails
- Part of the `rktweaks:cheaper_rails` datapack. Enabled by default.
![Rail Recipe](img/rail_recipe.png)
![Powered Rail Recipe](img/powered_rail_recipe.png)
![Detector Rail Recipe](img/detector_rail_recipe.png)
![Activator Rail Recipe](img/activator_rail_recipe.png)

### Cheaper maps
- Part of the `rktweaks:cheaper_maps` datapack. Enabled by default.
![Map Recipe](img/map_recipe.png)

# Gameplay Tweaks

### Leg armor negates sweet berry thorn damage
- Configurable with the new `gamerule rktweaks:leg_armor_protects_from_berry_thorns`, default `true`.

### All recipes are granted when joining server
- Configurable with the new `gamerule rktweaks:give_all_recipes`, default `true`.

### Flaming arrows ignite fire if they land on a flammable surface
- Configurable using the new `gamerule rktweaks:fire_arrows_light_fire`, default `true`.

### Iron golems regenerate over time
- Configurable using the new `gamerule rktweaks:iron_golem_regen_ticks`, default `60`.
- Value is number of ticks between each regeneration event, where the golem gains 1hp.
- `gamerule rktweaks:iron_golem_regen_delay` specifies the number of ticks to wait after the golem takes damage to resume healing.

### Creepers drop every block they break
- Configurable with the new `gamerule rktweaks:creepers_drop_all_blocks`, default `true`.

# Misc

### Farmer's Delight rope categorization
- Puts the rope in the right category so that it can be used in recipes that use `#c:ropes` as an ingredient.
- Part of the `rktweaks:farmersdelight_rope_compat` datapack. Disabled by default.
