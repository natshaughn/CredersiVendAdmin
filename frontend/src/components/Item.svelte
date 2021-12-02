<script>
    export let icon;
    export let item;
    export let select = null;

    $: displaying = (select != null);
    $: id = (item.uuid) ? item.uuid : null;
</script>

<style type="text/scss">
    @import 'src/styles/vars';

    displayable {
        display: flex;
        font-weight: bold;
    }

    img {
        display: flex;
    }

    item {
        align-items: center;
        background-color: $itemColorBack;
        border-radius: $itemBorderRadius;
        color: $itemColorFore;
        display: flex;
        flex-direction: row;
        gap: $gapSize;
        margin-bottom: $padding;
        margin-top: $padding;
        padding: $padding;
    }

    item.displaying:hover {
        cursor: pointer;
    }
    
    .icon {
		height: $iconSize;
		width: $iconSize;
    }
</style>

<item class:displaying {id} on:click={select}>
    <img alt="Customer Icon" class="icon" src="images/{icon}.svg"/>
    {#if displaying}
    <displayable>
        <slot class="displayable" name="displayable"></slot>
    </displayable>
    {:else}
    <slot name="creatable"></slot>
    {/if}
</item>