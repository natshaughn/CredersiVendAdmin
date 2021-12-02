<script>
    import {createEventDispatcher} from 'svelte';
    import Create from 'components/Create.svelte';
    import Item from 'components/Item.svelte';

    export let context;
    export let onCancel = null;
    export let onOK = null;
    export let selectSite = null;
    export let site;

    const dispatch = createEventDispatcher();
    const icon = 'site';
    
    $: customer = context.customer;
    $: domain = context.domain;
    $: item = site;
    $: select = (selectSite) ? selectSite : (site.uuid) ? () => dispatch('refresh', {customer, domain, site}) : null;
</script>

<Item {icon} {item} {select}>
    <span slot="displayable" style="width: 100%">
        <b>{site.name}</b>
        {#if site.address}<br>{site.address}{/if}
    </span>
    <span slot="creatable" style="width: 100%">
        {#if onOK}
        <Create {onCancel} {onOK} placeholder1="Enter site name" placeholder2="Enter site address"/>
        {/if}
    </span>
</Item>
