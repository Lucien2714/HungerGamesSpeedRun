package club.lucien2714.hungergamesspeedrun.events;

import club.lucien2714.hungergamesspeedrun.GameStatus.GameStatus;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.util.BoundingBox;
import org.bukkit.util.Vector;

public class protectArea implements Listener {
    Location c1 = GameStatus.worlds.get(0).getSpawnLocation().clone().add(10, 100, 10);
    Location c2 = GameStatus.worlds.get(0).getSpawnLocation().clone().add(-10, -50, -10);
    BoundingBox area = new BoundingBox(c1.getX(), c1.getY(), c1.getZ(), c2.getX(), c2.getY(), c2.getZ());

    @EventHandler
    public void protectArea(BlockBreakEvent e) {
        //System.out.println(c1);
        //System.out.println(c2);
        Block b = e.getBlock();
        Entity p = e.getPlayer();
        //System.out.println("breaked");
        Location player = p.getLocation();
        Vector pvec = new Vector(player.getX(), player.getY(), player.getZ());
        Location block = b.getLocation();
        Vector bvec = new Vector(block.getX(), block.getY(), block.getZ());
        //System.out.println(pvec);
        //System.out.println(bvec);
        //System.out.println(area.contains(pvec) || area.contains(bvec));
        if (area.contains(pvec) || area.contains(bvec)) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void protectArea(BlockPlaceEvent e) {
        System.out.println(c1);
        System.out.println(c2);
        Block b = e.getBlock();
        Entity p = e.getPlayer();
        //System.out.println("Placed");
        Location player = p.getLocation();
        Vector pvec = new Vector(player.getX(), player.getY(), player.getZ());
        Location block = b.getLocation();
        Vector bvec = new Vector(block.getX(), block.getY(), block.getZ());
        //System.out.println(pvec);
        //System.out.println(bvec);
        //System.out.println(area.contains(pvec) || area.contains(bvec));
        if (area.contains(pvec) || area.contains(bvec)) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void protectArea(CreatureSpawnEvent e) {
        //System.out.println(c1);
        //System.out.println(c2);
        Entity b = e.getEntity();
        if (b.getType() == EntityType.ZOMBIE || b.getType() == EntityType.SKELETON || b.getType() == EntityType.SPIDER || b.getType() == EntityType.ENDERMAN || b.getType() == EntityType.WITCH) {
            //System.out.println("Spawned");
            Location block = b.getLocation();
            Vector bvec = new Vector(block.getX(), block.getY(), block.getZ());
            //System.out.println(bvec);
            //System.out.println(area.contains(bvec));
            if (area.contains(bvec)) {
                b.remove();
            }
        }
    }


}
