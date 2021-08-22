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
import org.bukkit.event.entity.EntityChangeBlockEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.util.BoundingBox;
import org.bukkit.util.Vector;

public class protectArea implements Listener {
    Location c1 = GameStatus.worlds.get(0).getSpawnLocation().clone().add(10, 100, 10);
    Location c2 = GameStatus.worlds.get(0).getSpawnLocation().clone().add(-10, -50, -10);
    BoundingBox area = new BoundingBox(c1.getX(), c1.getY(), c1.getZ(), c2.getX(), c2.getY(), c2.getZ());

    Location creeper1 = GameStatus.worlds.get(0).getSpawnLocation().clone().add(20, 110, 20);
    Location creeper2 = GameStatus.worlds.get(0).getSpawnLocation().clone().add(-20, -60, -20);
    BoundingBox creeperarea = new BoundingBox(creeper1.getX(), creeper1.getY(), creeper1.getZ(), creeper2.getX(), creeper2.getY(), creeper2.getZ());

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

    @EventHandler
    public void protectArea(EntityDamageEvent e) {
        Location loc = e.getEntity().getLocation();
        Vector epos = new Vector(loc.getX(), loc.getY(), loc.getZ());
        if (area.contains(epos) && e.getEntity().getType() == EntityType.PLAYER) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void protectArea(EntityChangeBlockEvent e) {
        Entity entity = e.getEntity();
        if (entity.getType().equals(EntityType.ENDERMAN)) {
            System.out.println("enderman takes blocks");
            Block b = e.getBlock();
            Location loc = b.getLocation();
            Vector vec = new Vector(loc.getX(), loc.getY(), loc.getZ());
            if (area.contains(vec))
                e.setCancelled(true);
        }
    }

    @EventHandler
    public void protectArea(EntityExplodeEvent e) {
        Entity entity = e.getEntity();
        if (entity.getType().equals(EntityType.CREEPER)) {
            System.out.println("Creeper exploded");
            Location loc=entity.getLocation();
            Vector vec=new Vector(loc.getX(),loc.getY(),loc.getZ());
            if (area.contains(vec)) {
                e.setCancelled(true);
            }

        }
    }

}
