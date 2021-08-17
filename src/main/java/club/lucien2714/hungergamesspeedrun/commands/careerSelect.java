package club.lucien2714.hungergamesspeedrun.commands;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
//Let users choose their career
public class careerSelect implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            Inventory uiChoose = Bukkit.createInventory(player, 9, createComponent("职业选择"));
            ItemStack runner = new ItemStack(Material.DIAMOND_BOOTS);
            ItemMeta runnerMeta = runner.getItemMeta();
            runnerMeta.displayName(createComponent("奔跑者", TextColor.color(227, 23, 13)));
            List<Component> runnerDetail = new ArrayList<Component>();
            runnerDetail.add(createComponent("奔跑速度更快", TextColor.color(25, 90, 200)));
            runnerMeta.lore(runnerDetail);
            runnerMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
            runner.setItemMeta(runnerMeta);

            ItemStack collector = new ItemStack(Material.DIAMOND_PICKAXE);
            ItemMeta collectorMeta = collector.getItemMeta();
            collectorMeta.displayName(createComponent("收集者", TextColor.color(227, 23, 13)));
            List<Component> collectorDetail = new ArrayList<Component>();
            collectorDetail.add(createComponent("采集效率更高", TextColor.color(25, 90, 200)));
            collectorMeta.lore(collectorDetail);
            collectorMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
            collector.setItemMeta(collectorMeta);

            ItemStack attacker = new ItemStack(Material.DIAMOND_SWORD);
            ItemMeta attackerMeta = attacker.getItemMeta();
            attackerMeta.displayName(createComponent("战士", TextColor.color(227, 23, 13)));
            List<Component> attackerDetail = new ArrayList<Component>();
            attackerDetail.add(createComponent("伤害更高", TextColor.color(25, 90, 200)));
            attackerMeta.lore(attackerDetail);
            attackerMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
            attacker.setItemMeta(attackerMeta);

            uiChoose.addItem(attacker);
            uiChoose.addItem(runner);
            uiChoose.addItem(collector);
            player.openInventory(uiChoose);
        }

        return true;
    }
    //makes it easier to create a Component man I hate this
    private Component createComponent(@NotNull String text, @NotNull TextColor color) {
        return Component.text(text).color(color);
    }

    private Component createComponent(@NotNull String text) {
        return Component.text(text);
    }
}
