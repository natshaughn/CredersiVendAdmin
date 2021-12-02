<script>
    export let onCancel;
    export let onOK;
    export let placeholder1;
    export let placeholder2 = null;
    export let placeholder3 = null;

    let creating = false;
    let value1 = null;
    let value2 = null;
    let value3 = null;

    const cancel = (event) => {
        event.preventDefault();
        onCancel();
    }
    
    const create = (event) => {
        if (event) {
            event.preventDefault();
        }
        
        creating = true;
        onOK(value1, value2, value3);
    };

    const keypress = (event) => {
        if (event.charCode === 13) {
            event.preventDefault();
            create();
        }
    };
</script>

<style type="text/scss">
    @import 'src/styles/vars';

    create {
        display: flex;
        flex-direction: row;
        gap: $gapSize;
        width: 100%;
    }

    create button {
        display: flex;
    }

    create buttons {
        display: flex;
        flex-direction: row;
        gap: $gapSize;
        justify-content: flex-end;
    }

    create fields {
        display: flex;
        flex-direction: column;
        flex-grow: 1;
    }

    create input {
        background-color: transparent;
        border: none;
        display: flex;
        font-weight: bold;
    }

    create.creating button {
        visibility: hidden;
    }
</style>

<create class:creating>
    <fields>
        <input disabled={creating} type="text" placeholder={placeholder1} id="placeholder1" name="placeholder1" bind:value={value1} on:keypress={keypress}>
        {#if placeholder2}
        <input disabled={creating} type="text" placeholder={placeholder2} id="placeholder2" name="placeholder2" bind:value={value2} on:keypress={keypress}>
        {/if}
        {#if placeholder3}
        <input disabled={creating} type="text" placeholder={placeholder3} id="placeholder3" name="placeholder3" bind:value={value3} on:keypress={keypress}>
        {/if}
        <buttons>
            <button on:click|once|stopPropagation={cancel}>Cancel</button>
            <button on:click|once|stopPropagation={create}>OK</button>
        </buttons>
    </fields>
</create>