<script>
    import {createEventDispatcher} from 'svelte';
    import Create from 'components/Create.svelte';
    import Item from 'components/Item.svelte';

    export let context;
    export let machine;
    export let onCancel = null;
    export let onOK = null;
    export let selectMachine = null;

    const dispatch = createEventDispatcher();
    const icon = 'machine';
    
    $: customer = context.customer;
    $: domain = context.domain;
    $: machines = (context.machines && (context.machines.length > 0)) ? context.machines.concat([machine]) : [machine];
    $: item = machine;
    $: site = context.site;
    
    $: select = (selectMachine) ? selectMachine : (machine.uuid) ? () => dispatch('refresh', {customer, domain, machines, site}) : null;
</script>

<Item {icon} {item} {select}>
    <span slot="displayable" style="width: 100%">
        <b>{machine.name}</b>
        {#if machine.location}<br>{machine.location}{/if}
        {#if machine.directions}<br>{machine.directions}{/if}
    </span>
    <span slot="creatable" style="width: 100%">
        {#if onOK}
        <Create {onCancel} {onOK} placeholder1="Enter machine name" placeholder2="Enter machine location"/>
        {/if}
    </span>
</Item>
