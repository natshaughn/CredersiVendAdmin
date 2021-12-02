<script>
    export let create;
    export let creating = false;
    export let itemClass;
</script>

<style type="text/scss">
    @import 'src/styles/vars';

    bar {
        display: flex;
        justify-content: flex-end;
    }

    bar.creating {
        display: none;
    }
</style>

<bar class:creating>
    <button on:click={create}>
        Create {itemClass}
    </button>
</bar>
